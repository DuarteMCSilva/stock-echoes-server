package com.stockechoes.api.auth;

import com.stockechoes.api.GenericApiException;
import com.stockechoes.api.auth.forms.AuthCredentialsForm;
import com.stockechoes.api.auth.forms.RegisterForm;
import com.stockechoes.core.auth.jwt.JwtAuthService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@ApplicationScoped
public class AuthService {

    @Inject
    JwtAuthService jwtAuthService;

    @Inject
    AuthCredentialsRepository authCredentialsRepository;

    /**
     * Facade for configurable authentication methods.
     * @param credentials user credentials
     * @return authentication cookies
     */
    public NewCookie getAuthCookies(AuthCredentialsForm credentials) {
        return this.jwtAuthService.getJwtAuthCookie(credentials);
    }

    public void assertValidCredentials(AuthCredentialsForm credentials) {
        Optional<AuthCredentials> userOpt = authCredentialsRepository
                .findByUsernameOptional(credentials.getUsername());

        if(userOpt.isEmpty()) {
            throw new GenericApiException(Response.Status.BAD_REQUEST, "User or email not found");
        }

        AuthCredentials user = userOpt.get();

        boolean isPasswordCorrect = user.verifyPassword(credentials.getPassword());

        if(!isPasswordCorrect) {
            throw new GenericApiException(Response.Status.BAD_REQUEST, "Wrong password");
        }
    }

    @Transactional
    public void createAccount(RegisterForm registerForm) {
        this.assertUsernameAndEmailAvailable(registerForm.getUsername(), registerForm.getEmail());

        var authCred = new AuthCredentials(registerForm);

        this.authCredentialsRepository.persist(authCred);
    }

    private void assertUsernameAndEmailAvailable(String username, String email) {
        var userOpt = authCredentialsRepository.findByUsernameOrEmail(username, email);

        if(userOpt.isPresent()) {
            throw new GenericApiException(Response.Status.CONFLICT, "Username and/or email already exist");
        }
    }
}

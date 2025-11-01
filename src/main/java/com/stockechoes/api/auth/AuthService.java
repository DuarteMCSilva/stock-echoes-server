package com.stockechoes.api.auth;

import com.stockechoes.api.GenericApiException;
import com.stockechoes.api.accounts.Account;
import com.stockechoes.api.accounts.AccountRepository;
import com.stockechoes.api.accounts.customer.Customer;
import com.stockechoes.api.accounts.customer.CustomerRepository;
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

    @Inject
    AccountRepository accountRepository;

    @Inject
    CustomerRepository customerRepository;

    /**
     * Facade for configurable authentication methods.
     * @param credentials user credentials
     * @return authentication cookies
     */
    public NewCookie getAuthCookies(AuthCredentials credentials) {

        return this.jwtAuthService.getJwtAuthCookie(credentials);
    }

    public AuthCredentials findUserCredentialsOrThrow(String username) {
        Optional<AuthCredentials> userOpt = authCredentialsRepository
                .findByUsernameOptional(username);

        if(userOpt.isEmpty()) {
            throw new GenericApiException(Response.Status.BAD_REQUEST, "User or email not found");
        }

        return userOpt.get();
    }

    public void assertMatchingPassword(AuthCredentials user, String password) {
        boolean isPasswordCorrect = user.verifyPassword(password);

        if(!isPasswordCorrect) {
            throw new GenericApiException(Response.Status.BAD_REQUEST, "Wrong password");
        }
    }

    @Transactional
    public void createAccount(RegisterForm registerForm) {
        this.assertUsernameAndEmailAvailable(registerForm.getUsername(), registerForm.getEmail());

        var customer = new Customer(registerForm.getFirstName(), registerForm.getLastName());
        this.customerRepository.persist(customer);

        var account = new Account(customer, registerForm.getEmail());
        this.accountRepository.persist(account);

        var authCred = new AuthCredentials(account.id, registerForm);
        this.authCredentialsRepository.persist(authCred);
    }

    private void assertUsernameAndEmailAvailable(String username, String email) {
        var userOpt = authCredentialsRepository.findByUsernameOrEmail(username, email);

        if(userOpt.isPresent()) {
            throw new GenericApiException(Response.Status.CONFLICT, "Username and/or email already exist");
        }
    }
}

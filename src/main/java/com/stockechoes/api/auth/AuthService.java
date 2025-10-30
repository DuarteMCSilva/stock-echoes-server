package com.stockechoes.api.auth;

import com.stockechoes.api.auth.user.UserCredentialsForm;
import com.stockechoes.core.auth.jwt.JwtAuthService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.NewCookie;

@ApplicationScoped
public class AuthService {

    @Inject
    JwtAuthService jwtAuthService;

    /**
     * Facade for configurable authentication methods.
     * @param credentials
     * @return authentication cookies
     */
    public NewCookie getAuthCookies(UserCredentialsForm credentials) {
        return this.jwtAuthService.getJwtAuthCookie(credentials);
    }
}

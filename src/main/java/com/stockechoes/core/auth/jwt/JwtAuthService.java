package com.stockechoes.core.auth.jwt;

import com.stockechoes.api.auth.user.UserCredentialsForm;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.NewCookie;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Set;

@ApplicationScoped
public class JwtAuthService {

    @Inject
    JsonWebToken jwt;

    @ConfigProperty(name="echoes.jwt.max-age", defaultValue = "600")
    int jwtMaxAge;

    public NewCookie getJwtAuthCookie(UserCredentialsForm credentials) {
        int expiresIn = jwtMaxAge;
        String token = issueJwt(credentials, (long) expiresIn);

        return new NewCookie.Builder("jwt").value(token)
                .path("/").maxAge(expiresIn).httpOnly(true)
                .secure(false).comment("JWT Token")
                .build();
    }

    private String issueJwt(UserCredentialsForm credentials, Long expiresIn) {
        return Jwt.issuer("quarkus-jwt")
                .expiresIn(expiresIn)
                .upn(credentials.getUsername()) // Retrieve from user?
                .groups(Set.of("admin", "user")) // Retrieve from user
                .sign();
    }

}

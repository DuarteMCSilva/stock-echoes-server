package com.stockechoes.core.auth.jwt;

import com.stockechoes.api.auth.AuthCredentials;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.NewCookie;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class JwtAuthService {

    @ConfigProperty(name="echoes.jwt.max-age", defaultValue = "600")
    int jwtMaxAge;

    public NewCookie getJwtAuthCookie(AuthCredentials credentials) {
        int expiresIn = jwtMaxAge;
        String token = issueJwt(credentials, (long) expiresIn);

        return new NewCookie.Builder("jwt").value(token)
                .path("/").maxAge(expiresIn).httpOnly(true)
                .secure(false).comment("JWT Token")
                .build();
    }

    private String issueJwt(AuthCredentials credentials, Long expiresIn) {
        return Jwt.issuer("quarkus-jwt")
                .expiresIn(expiresIn)
                .upn(String.valueOf(credentials.getAccountId()))
                .subject(credentials.getUsername())
                .groups(credentials.getRole())
                .sign();
    }

}

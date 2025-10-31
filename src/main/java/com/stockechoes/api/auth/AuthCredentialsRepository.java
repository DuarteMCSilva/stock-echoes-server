package com.stockechoes.api.auth;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class AuthCredentialsRepository implements PanacheRepository<AuthCredentials> {

    public Optional<AuthCredentials> findByUsernameOptional(String username) {
        return find("username", username).firstResultOptional();
    }

    public Optional<AuthCredentials> findByUsernameOrEmail(String username, String email) {
        return find("username = ?1 OR email = ?2", username, email).firstResultOptional();
    }
}

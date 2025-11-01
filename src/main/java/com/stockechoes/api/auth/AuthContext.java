package com.stockechoes.api.auth;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Set;

@RequestScoped
public class AuthContext {
    @Inject
    JsonWebToken jwt;

    public Long getAccountId() {
        return Long.valueOf(jwt.getName());
    }

    public String getUsername() {
        return jwt.getSubject();
    }

    public Set<String> getRoles() {
        return jwt.getGroups();
    }

    public boolean hasRole(String role) {
        return jwt.getGroups().contains(role);
    }
}
package com.stockechoes.auth;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

@Entity
@UserDefinition
@Table(name = "se_users")
public class User extends PanacheEntity {

    @Username
    public String username;

    @Password
    public String password;
    /// See how to hash password: https://quarkus.io/guides/security-jpa

    @Roles
    public String role;

}

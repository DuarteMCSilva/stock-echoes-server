package com.stockechoes.api.auth;

import com.stockechoes.api.auth.forms.RegisterForm;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Entity
@UserDefinition
@NoArgsConstructor
@Data
@Table(name = "se_users")
public class AuthCredentials extends PanacheEntity {

    @Column(name="account_id")
    public Long accountId;

    @Username
    private String username;
    private String email;

    @Password
    private String password;
    /// See how to hash password: https://quarkus.io/guides/security-jpa

    @Roles
    private Set<String> role;

    public AuthCredentials(Long accountId, RegisterForm registerForm) {
        this.accountId = accountId;
        this.username = registerForm.getUsername();
        this.email = registerForm.getEmail();
        this.role = Collections.singleton(RolesEnum.USER.name());
        this.setPassword(registerForm.getPassword());
    }

    public void setPassword(String password) {
        this.password = BcryptUtil.bcryptHash(password);
    }

    public boolean verifyPassword(String plainText) {
        return BcryptUtil.matches(plainText, this.password);
    }
}

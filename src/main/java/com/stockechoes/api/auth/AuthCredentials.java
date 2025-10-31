package com.stockechoes.api.auth;

import com.stockechoes.api.auth.forms.RegisterForm;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@UserDefinition
@NoArgsConstructor
@Data
@Table(name = "se_users")
public class AuthCredentials extends PanacheEntity {

    @Username
    private String username;

    private String email;

    @Password
    private String password;
    /// See how to hash password: https://quarkus.io/guides/security-jpa

    @Roles
    private String role;

    public AuthCredentials(RegisterForm registerForm) {
        this.username = registerForm.getUsername();
        this.email = registerForm.getEmail();
        this.role = RolesEnum.USER.name();
        this.setPassword(registerForm.getPassword());
    }

    public void setPassword(String password) {
        this.password = BcryptUtil.bcryptHash(password);
    }

    public boolean verifyPassword(String plainText) {
        return BcryptUtil.matches(plainText, this.password);
    }
}

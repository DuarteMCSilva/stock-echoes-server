package com.stockechoes.api.auth.forms;

import lombok.Getter;

@Getter
public class RegisterForm {
    private String username;
    private String password;
    private String email;

    private String firstName;
    private String lastName;
}

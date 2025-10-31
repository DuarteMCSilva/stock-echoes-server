package com.stockechoes.api.auth;


import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class AuthCredentialsTests {

    @Test
    @DisplayName("should check if password matches")
    void shouldVerifyPassword() throws IOException {
        var storedCredentials = new AuthCredentials();
        storedCredentials.setPassword("lala");

        assertTrue(storedCredentials.verifyPassword("lala"));
    }

    @Test
    @DisplayName("should store encrypted password")
    void shouldStoreEncryptedPassword() throws IOException {
        var storedCredentials = new AuthCredentials();
        storedCredentials.setPassword("lala");

        assertTrue(storedCredentials.getPassword().contains("$2a$"));
    }

}

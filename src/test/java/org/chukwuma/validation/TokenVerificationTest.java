package org.chukwuma.validation;

import org.chukwuma.validation.User;
import org.chukwuma.validation.ValidationServiceConcurrently;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TokenVerificationTest {

    User user = new User();

    @BeforeEach
    public void setUp() {
        user.setUsername("chukwuma");
        user.setPassword("Password@123");
        user.setEmail("chukwuma@egwuonwu.com");
        user.setDob("1990-01-01");
    }
    @Test
    public void testValidToken() {
        String token = ValidationServiceConcurrently.validateUserConcurrently(user);
        String verified = ValidationServiceConcurrently.validateToken(token, user.getUsername());
        assertEquals("verification pass", verified);
    }

    @Test
    public void testInvalidToken() {
        String token = ValidationServiceConcurrently.validateUserConcurrently(user);
        String verified = ValidationServiceConcurrently.validateToken(token, "felix");
        assertEquals("verification fails", verified);
    }
}


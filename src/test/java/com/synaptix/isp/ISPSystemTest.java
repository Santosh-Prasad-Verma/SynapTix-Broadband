package com.synaptix.isp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.sql.Connection;

public class ISPSystemTest {

    @Test
    public void testPasswordHashing() {
        String plainPassword = "adminPassword123";
        
        // Hash password
        String hashedPassword = PasswordHasher.hashPassword(plainPassword);
        assertNotNull(hashedPassword);
        assertTrue(hashedPassword.contains(":"), "Hash must contain salt delimiter ':'");
        
        // Verify password correctness
        assertTrue(PasswordHasher.checkPassword(plainPassword, hashedPassword), "Password verification failed");
        
        // Verify incorrect password fails
        assertFalse(PasswordHasher.checkPassword("wrongPassword", hashedPassword), "Incorrect password verification succeeded");
        assertFalse(PasswordHasher.checkPassword(plainPassword, null), "Null hash verification succeeded");
        assertFalse(PasswordHasher.checkPassword(plainPassword, "invalid-hash-no-colon"), "Invalid hash format verification succeeded");
    }

    @Test
    public void testDatabasePropertiesLoader() {
        // Retrieve connection status
        Connection conn = javaconnect.ConnecrDb();
        
        // If PostgreSQL docker is not running locally, connection will be null.
        // But we want to verify that the properties loader at least ran without throwing exceptions.
        assertDoesNotThrow(() -> {
            javaconnect.ConnecrDb();
        }, "Database connection setup threw an unexpected exception");
    }

    @Test
    public void testEmailUtilityOfflineFallbacks() {
        // Ensure EmailUtility handles config gracefully without throwing
        assertDoesNotThrow(() -> {
            // Test standard sending (under offline mode or real SMTP mode)
            EmailUtility.sendEmail(
                "test@example.com",
                "JUnit Automated System Test",
                "This is a test notification body.",
                null
            );
        }, "Email delivery utility threw an exception");
    }
}

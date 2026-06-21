package com.example.generatorparoley;

import java.util.List;

public final class PasswordGeneratorSmokeTest {
    public static void main(String[] args) {
        PasswordGenerator generator = new PasswordGenerator();
        List<String> passwords = generator.generate(12, 15, true, true, true, true);
        assertCondition(passwords.size() == 15, "Expected 15 passwords");
        for (String password : passwords) {
            assertCondition(password.length() == 12, "Expected password length 12");
            assertCondition(password.matches("[a-zA-Z0-9!@#$%^&*]+"), "Unexpected character set");
        }

        boolean failed = false;
        try {
            generator.generate(12, 1, false, false, false, false);
        } catch (IllegalArgumentException expected) {
            failed = true;
        }
        assertCondition(failed, "Expected an error when no character type is selected");
    }

    private static void assertCondition(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}

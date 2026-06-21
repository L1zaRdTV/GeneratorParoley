package com.example.generatorparoley;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public final class PasswordGenerator {
    public static final int MIN_LENGTH = 6;
    public static final int MAX_LENGTH = 20;
    public static final int DEFAULT_LENGTH = 12;
    public static final int MIN_COUNT = 1;
    public static final int MAX_COUNT = 15;

    public static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String DIGITS = "0123456789";
    public static final String SPECIAL = "!@#$%^&*";

    private final SecureRandom random;

    public PasswordGenerator() {
        this(new SecureRandom());
    }

    PasswordGenerator(SecureRandom random) {
        this.random = random;
    }

    public List<String> generate(int length, int count, boolean includeLowercase,
            boolean includeUppercase, boolean includeDigits, boolean includeSpecial) {
        validateRange(length, MIN_LENGTH, MAX_LENGTH, "Длина пароля");
        validateRange(count, MIN_COUNT, MAX_COUNT, "Количество паролей");

        String alphabet = buildAlphabet(includeLowercase, includeUppercase, includeDigits, includeSpecial);
        if (alphabet.isEmpty()) {
            throw new IllegalArgumentException("Выберите хотя бы один тип символов");
        }

        List<String> passwords = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            passwords.add(generateOne(length, alphabet));
        }
        return passwords;
    }

    private String generateOne(int length, String alphabet) {
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return password.toString();
    }

    private static String buildAlphabet(boolean includeLowercase, boolean includeUppercase,
            boolean includeDigits, boolean includeSpecial) {
        StringBuilder alphabet = new StringBuilder();
        if (includeLowercase) alphabet.append(LOWERCASE);
        if (includeUppercase) alphabet.append(UPPERCASE);
        if (includeDigits) alphabet.append(DIGITS);
        if (includeSpecial) alphabet.append(SPECIAL);
        return alphabet.toString();
    }

    private static void validateRange(int value, int min, int max, String fieldName) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(fieldName + " должна быть от " + min + " до " + max);
        }
    }
}

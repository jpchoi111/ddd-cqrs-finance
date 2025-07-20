package com.mycorp.finance.customer.domain.model.vo;

import com.mycorp.finance.global.exception.domain.InvalidPasswordException;
import com.mycorp.finance.global.security.infrastructure.config.PasswordEncoder;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

/**
 * Value Object representing a Password.
 * <p>
 * Encapsulates password validation and encoding rules.
 * - Enforces immutability and consistency.
 * - Password must be already encrypted when constructed.
 * - Use {@link Password#encode(String, PasswordEncoder)} for creating instances.
 * </p>
 */
@Embeddable
public final class Password {

    private static final int MIN_LENGTH = 8;
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile(".*[!@#$%^&*()\\-_=+\\[\\]{};:'\",.<>/?\\\\|`~].*");

    @Column(name = "password", nullable = false)
    private final String value;

    protected Password() {
        this.value = null;
    }

    /**
     * Private constructor to enforce use of factory methods.
     */
    private Password(String encodedValue) {
        if (encodedValue == null || encodedValue.isBlank()) {
            throw new InvalidPasswordException("Password cannot be null or blank");
        }
        this.value = encodedValue;
    }

    /**
     * Factory method to encode a raw password.
     */
    public static Password encode(String rawPassword, PasswordEncoder passwordEncoder) {
        if (isInvalid(rawPassword)) {
            throw new InvalidPasswordException("Password must be at least " + MIN_LENGTH +
                    " characters, contain at least one uppercase letter and one special character.");
        }
        String encoded = passwordEncoder.encode(rawPassword);
        return new Password(encoded);
    }

    /**
     * Factory method to wrap an already encoded password (e.g. from DB).
     */
    public static Password fromHashed(String hashedValue) {
        return new Password(hashedValue);
    }

    public boolean matches(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.value);
    }

    private static boolean isInvalid(String value) {
        return value == null
                || value.trim().length() < MIN_LENGTH
                || !UPPERCASE_PATTERN.matcher(value).matches()
                || !SPECIAL_CHAR_PATTERN.matcher(value).matches();
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}
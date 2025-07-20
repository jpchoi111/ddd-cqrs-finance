package com.mycorp.finance.global.security.domain.model;

import com.mycorp.finance.customer.domain.model.vo.Email;
import com.mycorp.finance.customer.domain.model.vo.Password;
import com.mycorp.finance.global.security.infrastructure.config.PasswordEncoder;
import com.mycorp.finance.global.exception.domain.InvalidPasswordException;
import lombok.Getter;

import java.util.UUID;

/**
 * Domain model representing an authenticated user.
 * Handles authentication logic for login credentials.
 */
@Getter
public class AuthUser {

    private final UUID customerId;
    private final Email email;
    private final Password password;

    // Private constructor to enforce use of factory method
    private AuthUser(UUID customerId, Email email, Password password) {
        this.customerId = customerId;
        this.email = email;
        this.password = password;
    }

    /**
     * Factory method to create a new AuthUser.
     * Used during registration when credentials are initially saved.
     *
     * @param customerId ID of the customer
     * @param email      validated email
     * @param password   encoded password
     * @return new AuthUser instance
     */
    public static AuthUser register(UUID customerId, Email email, Password password) {

        return new AuthUser(customerId, email, password);
    }

    /**
     * Factory method to reconstruct an existing authenticated user
     * from persistent state (e.g., when loading from database).
     *
     * This method assumes the password has already been hashed and validated.
     * Use only when restoring an AuthUser from a trusted source like JPA.
     *
     * @param customerId Unique identifier of the customer
     * @param email Customer's email address
     * @param hashedPassword Previously hashed password
     * @return AuthUser domain object representing an existing user
     */
    public static AuthUser fromExisting(UUID customerId, Email email, Password hashedPassword) {
        return new AuthUser(customerId, email, hashedPassword);
    }

    /**
     * Validates login credentials by comparing raw password with stored encoded one.
     *
     * @param rawPassword     raw input from user
     * @param passwordEncoder encoder used for matching
     * @throws InvalidPasswordException if password does not match
     */
    public void validatePassword(String rawPassword, PasswordEncoder passwordEncoder) {
        if (!password.matches(rawPassword, passwordEncoder)) {
            throw new InvalidPasswordException("Invalid password");
        }
    }
}

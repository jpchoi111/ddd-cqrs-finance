package com.mycorp.finance.global.security.infrastructure.config;

/**
 * PasswordEncoder interface for password encryption and verification.
 * <p>
 * This abstraction allows encryption algorithms to be easily swapped without impacting the domain logic.
 * It should be implemented in the infrastructure layer.
 */
public interface PasswordEncoder {

    /**
     * Encrypts the raw (plain) password.
     *
     * @param rawPassword the plain password
     * @return encrypted (hashed) password
     */
    String encode(String rawPassword);

    /**
     * Verifies whether the provided raw password matches the encrypted one.
     *
     * @param rawPassword the plain password
     * @param encodedPassword the previously hashed password
     * @return true if matches; false otherwise
     */
    boolean matches(String rawPassword, String encodedPassword);
}

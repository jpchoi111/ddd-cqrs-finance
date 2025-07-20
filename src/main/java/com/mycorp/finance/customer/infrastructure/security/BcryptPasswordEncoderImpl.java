package com.mycorp.finance.customer.infrastructure.security;

import com.mycorp.finance.global.security.infrastructure.config.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Implementation of PasswordEncoder using BCrypt hashing algorithm.
 * <p>
 * BCrypt is widely used for password hashing due to its adaptive strength and resistance to brute-force attacks.
 */
@Component
public class BcryptPasswordEncoderImpl implements PasswordEncoder {

    private final BCryptPasswordEncoder delegate;

    public BcryptPasswordEncoderImpl() {
        this.delegate = new BCryptPasswordEncoder();
    }

    /**
     * Encrypts the raw password using BCrypt.
     *
     * @param rawPassword plain password
     * @return hashed password
     */
    @Override
    public String encode(String rawPassword) {
        return delegate.encode(rawPassword);
    }

    /**
     * Verifies if raw password matches the hashed password.
     *
     * @param rawPassword plain password
     * @param encodedPassword hashed password
     * @return true if passwords match, false otherwise
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return delegate.matches(rawPassword, encodedPassword);
    }
}

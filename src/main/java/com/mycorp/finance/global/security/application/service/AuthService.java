package com.mycorp.finance.global.security.application.service;

import com.mycorp.finance.customer.domain.model.vo.Email;

/**
 * Service interface for authentication operations.
 */
public interface AuthService {
    /**
     * Authenticates a user by email and raw password, returns a JWT token if successful.
     *
     * @param email user's email
     * @param rawPassword plaintext password input
     * @return JWT token string
     */
    String login(Email email, String rawPassword);
}
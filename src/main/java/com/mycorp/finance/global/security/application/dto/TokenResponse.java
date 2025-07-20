package com.mycorp.finance.global.security.application.dto;

/**
 * DTO representing the response payload containing a JWT access token.
 * Typically returned after a successful login.
 */
public record TokenResponse(
        String token
) {}
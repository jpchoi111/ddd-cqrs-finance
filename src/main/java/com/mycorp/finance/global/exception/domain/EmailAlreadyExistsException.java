package com.mycorp.finance.global.exception.domain;

/**
 * Thrown when attempting to register a customer with an email that already exists.
 */
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}

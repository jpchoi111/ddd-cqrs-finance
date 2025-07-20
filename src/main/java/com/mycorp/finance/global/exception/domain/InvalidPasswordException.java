package com.mycorp.finance.global.exception.domain;

/**
 * Exception thrown when a password format is invalid.
 */
public class InvalidPasswordException extends DomainException {

    public InvalidPasswordException(String message) {
        super(message);
    }
}

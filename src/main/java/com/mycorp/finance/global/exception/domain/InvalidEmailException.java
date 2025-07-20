package com.mycorp.finance.global.exception.domain;

/**
 * Exception thrown when an email format is invalid.
 */
public class InvalidEmailException extends DomainException {

    public InvalidEmailException(String message) {
        super(message);
    }
}

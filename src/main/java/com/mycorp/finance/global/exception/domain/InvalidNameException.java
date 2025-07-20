package com.mycorp.finance.global.exception.domain;

/**
 * Exception thrown when a name format is invalid.
 */
public class InvalidNameException extends DomainException {

    public InvalidNameException(String message) {
        super(message);
    }
}

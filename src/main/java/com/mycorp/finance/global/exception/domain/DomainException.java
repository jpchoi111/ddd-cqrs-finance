package com.mycorp.finance.global.exception.domain;

/**
 * Base exception for domain related errors.
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}

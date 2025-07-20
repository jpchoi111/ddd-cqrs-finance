package com.mycorp.finance.global.exception.domain;

/**
 * Exception thrown when trying to register a duplicate email.
 */
public class DuplicateEmailException extends DomainException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}

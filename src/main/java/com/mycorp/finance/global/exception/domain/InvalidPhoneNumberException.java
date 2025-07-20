package com.mycorp.finance.global.exception.domain;

/**
 * Exception thrown when a phone number format is invalid.
 */
public class InvalidPhoneNumberException extends DomainException {

    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}

package com.mycorp.finance.global.exception;

/**
 * Exception thrown when two Money objects have different currencies.
 */
public class CurrencyMismatchException extends RuntimeException {

    public CurrencyMismatchException(String message) {
        super(message);
    }
}

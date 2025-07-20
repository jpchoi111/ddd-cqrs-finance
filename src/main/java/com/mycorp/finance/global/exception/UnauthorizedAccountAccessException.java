package com.mycorp.finance.global.exception;

/**
 * Thrown when a customer tries to access an account they do not own.
 */
public class UnauthorizedAccountAccessException extends RuntimeException {

    public UnauthorizedAccountAccessException(String message) {
        super(message);
    }
}

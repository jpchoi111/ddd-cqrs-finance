package com.mycorp.finance.global.exception;

/**
 * Exception thrown when an invalid amount (e.g., negative) is used for a transaction.
 */
public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException(String message) {
        super(message);
    }
}

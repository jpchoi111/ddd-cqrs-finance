package com.mycorp.finance.global.exception;

/**
 * Thrown when attempting an invalid transfer between accounts.
 */
public class InvalidAccountTransferException extends RuntimeException {

    public InvalidAccountTransferException(String message) {
        super(message);
    }
}

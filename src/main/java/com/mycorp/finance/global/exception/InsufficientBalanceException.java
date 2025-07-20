package com.mycorp.finance.global.exception;

/**
 * Exception thrown when the account balance is insufficient for withdrawal or transfer.
 */
public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}

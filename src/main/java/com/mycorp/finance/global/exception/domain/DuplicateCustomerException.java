package com.mycorp.finance.global.exception.domain;

public class DuplicateCustomerException extends DomainException {

    public DuplicateCustomerException(String message) {
        super(message);
    }
}

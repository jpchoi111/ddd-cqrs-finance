package com.mycorp.finance.global.exception.domain;

public class CustomerNotFoundException extends DomainException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}

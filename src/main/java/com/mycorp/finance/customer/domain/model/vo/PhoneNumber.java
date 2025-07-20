package com.mycorp.finance.customer.domain.model.vo;

import com.mycorp.finance.global.exception.domain.InvalidPhoneNumberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public final class PhoneNumber {

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[1-9]\\d{1,14}$");

    @Column(name = "phone_number", nullable = false)
    private final String value;

    protected PhoneNumber() {
        this.value = null;
    }

    public PhoneNumber(String value) {
        if (!isValid(value)) {
            throw new InvalidPhoneNumberException("Invalid phone number format.");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private boolean isValid(String value) {

        return value != null && PHONE_PATTERN.matcher(value).matches();
    }
}

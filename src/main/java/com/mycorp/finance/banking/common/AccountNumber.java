package com.mycorp.finance.banking.common;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object representing a bank account number.
 * Encapsulates generation and validation rules.
 */
public class AccountNumber {

    private final String value;

    private AccountNumber(String value) {
        validate(value);
        this.value = value;
    }

    public static AccountNumber of(String value) {
        return new AccountNumber(value);
    }

    public static AccountNumber generate() {
        String raw = "ACCT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new AccountNumber(raw);
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be null or blank");
        }
        if (!value.matches("ACCT-[A-Z0-9]{8}")) {
            throw new IllegalArgumentException("Invalid account number format: " + value);
        }
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountNumber)) return false;
        AccountNumber that = (AccountNumber) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

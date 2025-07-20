package com.mycorp.finance.customer.domain.model.vo;

import com.mycorp.finance.global.exception.domain.InvalidEmailException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public final class Email {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Column(name = "email", nullable = false, unique = true)
    private final String value;

    protected Email() {
        this.value = null;
    }

    public Email(String value) {
        if (!isValid(value)) {
            throw new InvalidEmailException("Invalid email format.");
        }
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public boolean isSameAs(String other) {
        return value.equalsIgnoreCase(other);
    }

    private boolean isValid(String value) {
        return value != null && EMAIL_PATTERN.matcher(value).matches();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;
        return value.equalsIgnoreCase(email.value);
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }
}

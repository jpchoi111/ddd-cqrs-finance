package com.mycorp.finance.customer.domain.model.vo;

import com.mycorp.finance.global.exception.domain.InvalidNameException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class Name {

    @Column(name = "first_name", nullable = false)
    private final String firstName;

    @Column(name = "middle_name")
    private final String middleName;

    @Column(name = "last_name", nullable = false)
    private final String lastName;

    protected Name() {
        this.firstName = null;
        this.middleName = null;
        this.lastName = null;
    }

    public Name(String firstName, String middleName, String lastName) {
        if (isBlank(firstName) || isBlank(lastName)) {
            throw new InvalidNameException("First name and last name must be provided.");
        }
        this.firstName = firstName;
        this.middleName = middleName; // null or empty value
        this.lastName = lastName;
    }

    public String fullName() {
        if (middleName == null || middleName.isBlank()) {
            return String.format("%s %s", firstName, lastName);
        }
        return String.format("%s %s %s", firstName, middleName, lastName);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    //getter
    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }
}

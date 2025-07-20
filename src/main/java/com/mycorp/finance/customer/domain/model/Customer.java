package com.mycorp.finance.customer.domain.model;

import com.mycorp.finance.customer.domain.model.enums.CustomerStatus;
import com.mycorp.finance.customer.domain.model.vo.*;
import com.mycorp.finance.global.base.AggregateRoot;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Customer aggregate root.
 * Immutable domain model.
 */
public final class Customer extends AggregateRoot<UUID> {

    private final Name name;
    private final Email email;
    private final Address address;
    private final PhoneNumber phoneNumber;
    private final Password password;
    private final LocalDate birthDate;
    private final CustomerStatus status;

    /**
     * Create new customer for registration.
     */
    public static Customer register(Name name, Email email, Address address, PhoneNumber phoneNumber, Password password, LocalDate birthDate) {
        return new Customer(UUID.randomUUID(), name, email, address, phoneNumber, password, birthDate, CustomerStatus.ACTIVE);
    }

    /**
     * Reconstruct customer from persistence.
     */
    public static Customer reconstruct(UUID id, Name name, Email email, Address address, PhoneNumber phoneNumber, Password password, LocalDate birthDate, CustomerStatus status) {
        return new Customer(id, name, email, address, phoneNumber, password, birthDate, status);
    }

    private Customer(UUID id, Name name, Email email, Address address, PhoneNumber phoneNumber, Password password, LocalDate birthDate, CustomerStatus status) {
        super(id);
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthDate = birthDate;
        this.status = status;
    }

    /**
     * Create new customer with updated personal info.
     */
    public static Customer update(Customer original, Name name, Address address, PhoneNumber phoneNumber) {
        return new Customer(
                original.getId(),
                name,
                original.getEmail(),
                address,
                phoneNumber,
                original.getPassword(),
                original.getBirthDate(),
                original.getStatus()
        );
    }

    /**
     * Deactivate customer.
     */
    public Customer deactivate() {
        return new Customer(this.getId(), this.name, this.email, this.address, this.phoneNumber, this.password, this.birthDate, CustomerStatus.INACTIVE);
    }

    public Name getName() { return name; }
    public Email getEmail() { return email; }
    public Address getAddress() { return address; }
    public PhoneNumber getPhoneNumber() { return phoneNumber; }
    public Password getPassword() { return password; }
    public LocalDate getBirthDate() { return birthDate; }
    public CustomerStatus getStatus() { return status; }
}

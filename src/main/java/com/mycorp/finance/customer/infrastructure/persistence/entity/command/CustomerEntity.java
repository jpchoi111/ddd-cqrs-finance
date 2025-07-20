package com.mycorp.finance.customer.infrastructure.persistence.entity.command;

import com.mycorp.finance.customer.domain.model.enums.CustomerStatus;
import com.mycorp.finance.customer.domain.model.vo.*;
import com.mycorp.finance.global.base.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Customer JPA entity.
 * <p>
 * Designed with DDD principles:
 * - Immutable after creation except for lifecycle status changes
 * - No setters to enforce invariants
 * - Protected no-args constructor for JPA only
 */
@Entity
@Table(name = "customers")
public class CustomerEntity extends BaseEntity {

    @Embedded
    private Name name;

    @Embedded
    private Email email;

    @Embedded
    private Address address;

    @Embedded
    private PhoneNumber phoneNumber;

    @Embedded
    private Password password;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status;

    /**
     * Protected no-args constructor for JPA proxy instantiation only.
     */
    protected CustomerEntity() {
        super();
    }

    /**
     * Constructor for creating fully-initialized CustomerEntity.
     *
     * @param id UUID of the customer (managed by BaseEntity)
     * @param name Customer's Name VO
     * @param email Customer's Email VO
     * @param address Customer's Address VO
     * @param phoneNumber Customer's PhoneNumber VO
     * @param password Customer's Password VO
     * @param birthDate Customer's birth date
     * @param status CustomerStatus enum
     */
    public CustomerEntity(
            UUID id,
            Name name,
            Email email,
            Address address,
            PhoneNumber phoneNumber,
            Password password,
            LocalDate birthDate,
            CustomerStatus status) {
        super(id);
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthDate = birthDate;
        this.status = status != null ? status : CustomerStatus.ACTIVE;
    }

    /**
     * JPA lifecycle callback to ensure status default.
     */
    @PrePersist
    protected void onPrePersist() {
        if (this.status == null) {
            this.status = CustomerStatus.ACTIVE;
        }
    }

    // Getters

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public Password getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public CustomerStatus getStatus() {
        return status;
    }
}

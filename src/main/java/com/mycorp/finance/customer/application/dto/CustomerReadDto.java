package com.mycorp.finance.customer.application.dto;

import com.mycorp.finance.customer.infrastructure.persistence.entity.query.CustomerReadEntity;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object for read-only Customer data.
 * Immutable and designed for safe data exposure to clients.
 */
@Getter
public class CustomerReadDto {

    private final UUID id;

    private final String name;

    private final String address;

    private final String phoneNumber;

    private final String email;

    private final LocalDate birthDate;

    private CustomerReadDto(UUID id, String name, String address, String phoneNumber, String email, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDate = birthDate;
    }

    /**
     * Factory method to create DTO from CustomerReadEntity.
     * Extracts all relevant fields from entity and embedded VOs.
     *
     * @param entity the CustomerReadEntity from DB
     * @return a fully populated CustomerReadDto
     */
    public static CustomerReadDto from(CustomerReadEntity entity) {
        return new CustomerReadDto(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getBirthDate()
        );
    }
}

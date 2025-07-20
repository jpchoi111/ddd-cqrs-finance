package com.mycorp.finance.customer.infrastructure.persistence.mapper;

import com.mycorp.finance.customer.domain.model.Customer;
import com.mycorp.finance.customer.infrastructure.persistence.entity.command.CustomerEntity;

/**
 * Mapper for converting between Customer domain model and CustomerEntity persistence model.
 * Designed for immutability and consistency.
 */
public class CustomerMapper {

    /**
     * Convert Customer domain aggregate to JPA entity.
     *
     * @param customer Domain aggregate root instance
     * @return CustomerEntity ready for persistence
     */
    public static CustomerEntity toEntity(Customer customer) {
        return new CustomerEntity(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getPassword(),
                customer.getBirthDate(),
                customer.getStatus()
        );
    }

    /**
     * Convert CustomerEntity to domain aggregate.
     *
     * @param entity persistence entity
     * @return reconstructed Customer aggregate root
     */
    public static Customer toDomain(CustomerEntity entity) {
        return Customer.reconstruct(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getPhoneNumber(),
                entity.getPassword(),
                entity.getBirthDate(),
                entity.getStatus()
        );
    }
}

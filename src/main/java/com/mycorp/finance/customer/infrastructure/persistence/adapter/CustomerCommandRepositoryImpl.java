package com.mycorp.finance.customer.infrastructure.persistence.adapter;

import com.mycorp.finance.customer.domain.model.Customer;
import com.mycorp.finance.customer.domain.model.vo.Email;
import com.mycorp.finance.customer.domain.repository.CustomerCommandRepository;
import com.mycorp.finance.customer.infrastructure.persistence.entity.command.CustomerEntity;
import com.mycorp.finance.customer.infrastructure.persistence.mapper.CustomerMapper;
import com.mycorp.finance.customer.infrastructure.persistence.repository.command.CustomerCommandJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementation of the CustomerCommandRepository.
 * Handles persistence logic for the Customer aggregate using JPA.
 */
@Repository
@Transactional("commandTransactionManager")
@RequiredArgsConstructor
public class CustomerCommandRepositoryImpl implements CustomerCommandRepository {

    private final CustomerCommandJpaRepository customerCommandJpaRepository;

    /**
     * Persists or updates a customer aggregate.
     *
     * @param customer the customer domain object to save
     */
    @Override
    public void save(Customer customer) {
        CustomerEntity entity = CustomerMapper.toEntity(customer);
        customerCommandJpaRepository.save(entity);
    }

    /**
     * Deletes a customer by its domain identifier.
     *
     * @param customer the customer to delete
     */
    @Override
    public void delete(Customer customer) {
        customerCommandJpaRepository.deleteById(customer.getId());
    }

    /**
     * Finds a customer by UUID.
     *
     * @param id the UUID of the customer
     * @return optional customer domain object
     */
    @Override
    public Optional<Customer> findById(UUID id) {
        return customerCommandJpaRepository.findById(id)
                .map(CustomerMapper::toDomain);
    }

    /**
     * Finds a customer by email.
     *
     * @param email the email value object
     * @return optional customer domain object
     */
    @Override
    public Optional<Customer> findByEmail(Email email) {
        return customerCommandJpaRepository.findByEmail(email.value())
                .map(CustomerMapper::toDomain);
    }
}

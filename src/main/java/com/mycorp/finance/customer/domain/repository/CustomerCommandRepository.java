package com.mycorp.finance.customer.domain.repository;

import com.mycorp.finance.customer.domain.model.Customer;
import com.mycorp.finance.customer.domain.model.vo.Email;

import java.util.Optional;
import java.util.UUID;

/**
 * Command-side repository interface for the Customer aggregate.
 * Handles create, update, and delete operations.
 */
public interface CustomerCommandRepository {

    /**
     * Finds a customer by its aggregate identifier (UUID).
     *
     * @param id the UUID of the customer
     * @return optional customer, empty if not found
     */
    Optional<Customer> findById(UUID id);

    /**
     * Finds a customer by its email.
     *
     * @param email the customer's email
     * @return optional customer, empty if not found
     */
    Optional<Customer> findByEmail(Email email);

    /**
     * Saves a new or existing customer.
     *
     * @param customer the customer to persist
     */
    void save(Customer customer);

    /**
     * Deletes a customer from the store.
     *
     * @param customer the customer to delete
     */
    void delete(Customer customer);
}

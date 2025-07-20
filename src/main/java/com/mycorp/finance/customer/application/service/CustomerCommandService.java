package com.mycorp.finance.customer.application.service;

import com.mycorp.finance.customer.application.dto.CustomerRegisterCommand;
import com.mycorp.finance.customer.application.dto.CustomerUpdateCommand;

import java.util.UUID;

/**
 * Service interface for customer write operations.
 */
public interface CustomerCommandService {

    /**
     * Register a new customer.
     *
     * @param command the customer registration data
     */
    void registerCustomer(CustomerRegisterCommand command);

    /**
     * Update an existing customer.
     *
     * @param customerId the ID of the customer to update
     * @param command the customer update data
     */
    void updateCustomer(UUID customerId, CustomerUpdateCommand command);

    /**
     * Delete a customer by ID.
     *
     * @param customerId the ID of the customer to delete
     */
    void deleteCustomer(UUID customerId);
}

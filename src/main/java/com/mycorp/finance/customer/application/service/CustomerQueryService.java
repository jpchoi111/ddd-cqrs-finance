package com.mycorp.finance.customer.application.service;

import com.mycorp.finance.customer.application.dto.CustomerReadDto;

import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for customer query use cases.
 * Provides methods to fetch customer data in read-only fashion.
 */
public interface CustomerQueryService {

    /**
     * Retrieves a customer by their unique identifier.
     *
     * @param customerId the UUID of the customer
     * @return the customer data transfer object wrapped in Optional,
     *         empty if customer not found
     */
    Optional<CustomerReadDto> getCustomerById(UUID customerId);
}


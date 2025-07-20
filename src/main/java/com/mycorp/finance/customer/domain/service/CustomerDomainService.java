package com.mycorp.finance.customer.domain.service;

import com.mycorp.finance.customer.domain.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerDomainService {

    /**
     * Check if the customer is duplicate.
     */
    boolean isDuplicate(Customer customer);

    /**
     * Check if the customer is valid.
     */
    void validateCustomer(Customer customer);
}

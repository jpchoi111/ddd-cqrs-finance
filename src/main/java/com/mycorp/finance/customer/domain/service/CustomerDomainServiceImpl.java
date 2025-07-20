package com.mycorp.finance.customer.domain.service;

import com.mycorp.finance.customer.domain.model.Customer;
import com.mycorp.finance.customer.domain.repository.CustomerQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerDomainServiceImpl implements CustomerDomainService {

    private final CustomerQueryRepository customerQueryRepository;

    /**
     * Check if the customer is duplicate (based on email).
     */
    @Override
    public boolean isDuplicate(Customer customer) {
        return customerQueryRepository.existsByEmail(customer.getEmail());
    }

    /**
     * Validates the given customer entity according to domain rules.
     *
     * This implementation currently checks for duplicate customers based on email.
     * Throws IllegalArgumentException if a duplicate customer is found.
     *
     * @param customer the customer entity to validate
     * @throws IllegalArgumentException if validation fails (e.g., duplicate email)
     */
    @Override
    public void validateCustomer(Customer customer) {
        if (isDuplicate(customer)) {
            throw new IllegalArgumentException("Duplicate customer with same email exists.");
        }
    }
}

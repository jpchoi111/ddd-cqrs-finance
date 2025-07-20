package com.mycorp.finance.customer.application.service;

import com.mycorp.finance.customer.application.dto.CustomerReadDto;
import com.mycorp.finance.customer.domain.repository.CustomerQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of CustomerQueryService interface.
 * Handles read-only customer data retrieval logic.
 */
@Service
@Transactional(readOnly = true)
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final CustomerQueryRepository customerQueryRepository;

    public CustomerQueryServiceImpl(CustomerQueryRepository customerQueryRepository) {
        this.customerQueryRepository = customerQueryRepository;
    }

    /**
     * Fetches customer information by business ID (customerId).
     * Converts customerId string to UUID and delegates to repository.
     * Returns empty if UUID conversion fails or entity is not found.
     *
     * @param customerId the unique business identifier of the customer
     * @return Optional wrapping CustomerReadDto or empty if not found
     */
    @Override
    public Optional<CustomerReadDto> getCustomerById(UUID customerId) {
            return customerQueryRepository.findById(customerId);
    }
}

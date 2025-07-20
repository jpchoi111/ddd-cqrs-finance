package com.mycorp.finance.customer.domain.repository;

import com.mycorp.finance.customer.application.dto.CustomerReadDto;
import com.mycorp.finance.customer.domain.model.vo.Email;

import java.util.Optional;
import java.util.UUID;

public interface CustomerQueryRepository {

    /**
     * Check if a customer exists by email.
     */
    boolean existsByEmail(Email email);

    /**
     * Find customer by ID and email.
     */
    Optional<CustomerReadDto> findById(UUID id);
    Optional<CustomerReadDto> findByEmail(Email email);
}

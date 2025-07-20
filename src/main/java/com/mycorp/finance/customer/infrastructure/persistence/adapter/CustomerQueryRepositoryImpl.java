package com.mycorp.finance.customer.infrastructure.persistence.adapter;

import com.mycorp.finance.customer.application.dto.CustomerReadDto;
import com.mycorp.finance.customer.domain.model.vo.Email;
import com.mycorp.finance.customer.domain.repository.CustomerQueryRepository;
import com.mycorp.finance.customer.infrastructure.persistence.repository.query.CustomerQueryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementation of the CustomerQueryRepository using Spring Data JPA.
 * Delegates query responsibilities to the CustomerQueryJpaRepository interface.
 */
@Repository
@Transactional("queryTransactionManager")
@RequiredArgsConstructor
public class CustomerQueryRepositoryImpl implements CustomerQueryRepository {

    private final CustomerQueryJpaRepository customerQueryJpaRepository;

    /**
     * Checks whether a customer with the given email already exists.
     *
     * @param email the email to check
     * @return true if a customer exists with that email, false otherwise
     */
    @Override
    public boolean existsByEmail(Email email) {
        return customerQueryJpaRepository.existsByEmail(email.value());
    }

    /**
     * Finds a CustomerReadEntity by its UUID primary key.
     *
     * @param id the UUID of the customer entity
     * @return an optional CustomerReadDto if found
     */
    @Override
    public Optional<CustomerReadDto> findById(UUID id) {
        return customerQueryJpaRepository.findById(id)
                .map(CustomerReadDto::from);
    }

    /**
     * Finds a CustomerReadEntity by its email address.
     *
     * @param email the customer's email
     * @return an optional CustomerReadDto if found
     */
    @Override
    public Optional<CustomerReadDto> findByEmail(Email email) {
        return customerQueryJpaRepository.findByEmail(email.value())
                .map(CustomerReadDto::from);  // Entity → DTO
    }
}

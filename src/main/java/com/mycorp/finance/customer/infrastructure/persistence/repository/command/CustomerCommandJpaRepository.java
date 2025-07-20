package com.mycorp.finance.customer.infrastructure.persistence.repository.command;

import com.mycorp.finance.customer.infrastructure.persistence.entity.command.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for CustomerEntity (write-side persistence).
 *
 * Used by the command adapter to persist and retrieve Customer aggregates.
 * All operations here are typically invoked by the write-side application service layer.
 */
@Repository
public interface CustomerCommandJpaRepository extends JpaRepository<CustomerEntity, UUID> {

    /**
     * Finds a customer entity by email.
     * Used to support domain-level uniqueness checks or login use cases.
     *
     * @param email the email address to look up
     * @return Optional of CustomerEntity if found
     */
    Optional<CustomerEntity> findByEmail(String email);

    // Other custom command-side queries (e.g. for status-based deletion) can be defined here if needed
}

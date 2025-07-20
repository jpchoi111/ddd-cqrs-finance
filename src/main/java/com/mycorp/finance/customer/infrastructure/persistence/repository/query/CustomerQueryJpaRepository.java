package com.mycorp.finance.customer.infrastructure.persistence.repository.query;

import com.mycorp.finance.customer.infrastructure.persistence.entity.query.CustomerReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for read-only access to customer data.
 * Used as part of the query-side adapter in a CQRS architecture.
 */
@Repository
public interface CustomerQueryJpaRepository extends JpaRepository<CustomerReadEntity, UUID> {

    /**
     * Find a customer entity by email address.
     *
     * @param email customer's email string
     * @return optional customer entity
     */
    Optional<CustomerReadEntity> findByEmail(String email);

    /**
     * Check existence of customer by email.
     *
     * @param email customer's email string
     * @return true if exists
     */
    boolean existsByEmail(String email);
}

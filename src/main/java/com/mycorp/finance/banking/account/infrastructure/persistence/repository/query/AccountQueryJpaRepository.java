package com.mycorp.finance.banking.account.infrastructure.persistence.repository.query;

import com.mycorp.finance.banking.account.infrastructure.persistence.entity.query.AccountReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository interface for AccountReadEntity.
 * Provides CRUD operations and custom queries for accounts.
 *
 * The primary key is UUID, while accountNumber is a unique business key.
 */
public interface AccountQueryJpaRepository extends JpaRepository<AccountReadEntity, UUID> {

    /**
     * Retrieves an account entity by its unique business identifier (accountNumber).
     *
     * @param accountNumber the unique account number string
     * @return an Optional containing the AccountReadEntity if found, or empty otherwise
     */
    Optional<AccountReadEntity> findByAccountNumber(String accountNumber);

    /**
     * Checks whether an account exists with the given business key (accountNumber).
     *
     * @param accountNumber the unique account number string
     * @return true if an account with the given accountNumber exists, false otherwise
     */
    boolean existsByAccountNumber(String accountNumber);
}


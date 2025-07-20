package com.mycorp.finance.banking.account.infrastructure.persistence.repository.command;

import com.mycorp.finance.banking.account.infrastructure.persistence.entity.command.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository interface for AccountEntity.
 * Provides CRUD and query methods for account persistence.
 */
@Repository
public interface AccountCommandJpaRepository extends JpaRepository<AccountEntity, UUID> {

    /**
     * Finds an account entity by its unique account number.
     *
     * @param accountNumber the unique account number to search by
     * @return an Optional containing the found AccountEntity, or empty if none found
     */
    Optional<AccountEntity> findByAccountNumber(String accountNumber);

    /**
     * Checks whether an account exists with the given account number.
     *
     * @param accountNumber the account number to check existence for
     * @return true if an account with the given account number exists, false otherwise
     */
    boolean existsByAccountNumber(String accountNumber);
}

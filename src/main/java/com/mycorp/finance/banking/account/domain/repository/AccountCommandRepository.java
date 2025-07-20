package com.mycorp.finance.banking.account.domain.repository;

import com.mycorp.finance.banking.account.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for performing write operations on Account aggregate.
 * Responsible for creating, updating, and deleting accounts.
 */
public interface AccountCommandRepository {

    /**
     * Finds an Account by its unique identifier.
     *
     * @param accountId the UUID of the Account to find
     * @return an Optional containing the Account if found, or empty if not found
     */
    Optional<Account> findById(UUID accountId);

    /**
     * Saves a new or updated account to the repository.
     *
     * @param account the account to save
     * @return the saved account instance
     */
    Account save(Account account);

    /**
     * Deletes an account by its unique identifier.
     *
     * @param accountId the id of the account to delete
     */
    void deleteById(UUID accountId);
}

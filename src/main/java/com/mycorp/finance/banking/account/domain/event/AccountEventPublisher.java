package com.mycorp.finance.banking.account.domain.event;

import com.mycorp.finance.banking.account.domain.model.Account;

/**
 * Publishes account-related domain events to an external messaging system.
 * This abstraction allows the application layer to remain decoupled from infrastructure.
 */
public interface AccountEventPublisher {

    /**
     * Publishes an event when a new account has been successfully created.
     *
     * @param account the newly created account
     */
    void publishAccountCreated(Account account);

    /**
     * Publishes an event when an existing account has been updated.
     *
     * @param account the updated account
     */
    void publishAccountUpdated(Account account);

    void publishAccountDeleted(Account account);
}

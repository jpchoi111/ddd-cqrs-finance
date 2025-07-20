package com.mycorp.finance.banking.account.application.service;

import com.mycorp.finance.banking.account.application.dto.AccountCreateCommand;
import com.mycorp.finance.banking.account.application.dto.AccountUpdateCommand;

/**
 * Service interface defining command operations for Account aggregate.
 * Handles creation and update of accounts.
 */
public interface AccountCommandService {

    /**
     * Creates a new account using the provided command data.
     *
     * @param command DTO containing account creation information
     */
    void createAccount(AccountCreateCommand command);

    /**
     * Updates an existing account with the provided command data.
     *
     * @param command DTO containing account update information
     */
    void updateAccount(AccountUpdateCommand command);
}

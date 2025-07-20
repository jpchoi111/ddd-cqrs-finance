package com.mycorp.finance.banking.account.application.service;

import com.mycorp.finance.banking.account.application.dto.AccountReadDto;
import com.mycorp.finance.banking.common.AccountNumber;

import java.util.Optional;

/**
 * Service interface for querying Account data.
 * Provides methods to retrieve account information and verify existence.
 */
public interface AccountQueryService {

    /**
     * Finds an account by its unique business identifier (account number).
     *
     * @param accountNumber the account number value object
     * @return an Optional containing AccountReadDto if found, or empty if not found
     */
    Optional<AccountReadDto> findByAccountNumber(AccountNumber accountNumber);

    /**
     * Checks if an account exists with the given account number.
     *
     * @param accountNumber the account number value object
     * @return true if an account with the account number exists, false otherwise
     */
    boolean existsByAccountNumber(AccountNumber accountNumber);
}

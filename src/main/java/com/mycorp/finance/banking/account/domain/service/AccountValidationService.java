package com.mycorp.finance.banking.account.domain.service;

import com.mycorp.finance.banking.account.application.dto.AccountCreateCommand;
import com.mycorp.finance.banking.account.application.dto.AccountUpdateCommand;
import com.mycorp.finance.banking.account.domain.model.Account;
import com.mycorp.finance.banking.account.domain.model.enums.AccountStatus;
import com.mycorp.finance.banking.common.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Validates commands or cross-aggregate rules related to Account before domain operation.
 * Domain invariants should be validated within the Account entity itself.
 */
@Service
public class AccountValidationService {

    /**
     * Validates fields or business constraints before account creation.
     *
     * @param command the create command DTO
     */
    public void validateForCreate(AccountCreateCommand command) {
        if (command.initialBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance must be zero or positive");
        }
        // Additional rules like verifying customer existence could be placed here or in a domain service
    }

    /**
     * Validates the account update request before status change.
     * Assumes status change will be handled in domain with internal checks.
     *
     * @param command the update command DTO
     */
    public void validateForUpdate(AccountUpdateCommand command) {
        try {
            AccountStatus.valueOf(command.status());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid account status: " + command.status(), ex);
        }
    }

    /**
     * Validates whether the account can be closed.
     *
     * @param account the account to check
     */
    public void validateClose(Account account) {
        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new IllegalStateException("Account is already closed.");
        }
        if (account.getStatus() == AccountStatus.PENDING || account.getStatus() == AccountStatus.SUSPENDED) {
            throw new IllegalStateException("Account cannot be closed in its current state.");
        }
    }

    /**
     * Validates whether the account can be reactivated.
     *
     * @param account the account to check
     */
    public void validateReactivate(Account account) {
        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new IllegalStateException("Closed accounts cannot be reactivated.");
        }
        if (account.getStatus() == AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is already active.");
        }
    }

    /**
     * Validates whether the account can be marked as dormant.
     *
     * @param account the account to check
     */
    public void validateDormant(Account account) {
        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new IllegalStateException("Closed accounts cannot be marked dormant.");
        }
        if (account.getStatus() == AccountStatus.DORMANT) {
            throw new IllegalStateException("Account is already dormant.");
        }
    }

    /**
     * Optional: Validates a withdrawal operation by status and balance.
     *
     * @param account the account to check
     * @param amount  amount to withdraw
     */
    public void validateWithdrawal(Account account, Money amount) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Only active accounts can withdraw.");
        }
        if (account.getBalance().isLessThan(amount)) {
            throw new IllegalStateException("Insufficient balance.");
        }
    }

    /**
     * Optional: Validates a deposit operation.
     *
     * @param account the account to check
     */
    public void validateDeposit(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Only active accounts can receive deposits.");
        }
    }
}

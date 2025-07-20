package com.mycorp.finance.banking.account.domain.model;

import com.mycorp.finance.banking.account.domain.model.enums.AccountStatus;
import com.mycorp.finance.banking.common.Money;
import com.mycorp.finance.banking.common.AccountNumber;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Aggregate root representing a bank account.
 * Encapsulates business rules related to account lifecycle and state transitions.
 * Manages balance, status, and consistent creation/update timestamps.
 */
public class Account {

    private final UUID accountId;
    private final AccountNumber accountNumber;
    private final UUID customerId;
    private Money balance;
    private AccountStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Private constructor for factory and reconstruction
    private Account(UUID accountId,
                    AccountNumber accountNumber,
                    UUID customerId,
                    Money balance,
                    AccountStatus status,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method to create a new active account with consistent timestamps.
     *
     * @param customerId     Owner customer UUID
     * @param initialBalance Initial deposit amount wrapped in Money
     * @param clock          Clock instance to provide consistent timestamps
     * @return Newly created Account aggregate
     */
    public static Account createNewAccount(UUID customerId, Money initialBalance, Clock clock) {
        LocalDateTime now = LocalDateTime.now(clock);
        return new Account(
                UUID.randomUUID(),
                AccountNumber.generate(),
                customerId,
                initialBalance,
                AccountStatus.ACTIVE,
                now,
                now
        );
    }

    /**
     * Reconstruct an existing Account from persistence data.
     * Used by mappers to rebuild domain model.
     *
     * @param accountId     Account UUID
     * @param accountNumber AccountNumber value object
     * @param customerId    Owner customer UUID
     * @param balance       Account balance wrapped in Money
     * @param status        AccountStatus enum
     * @param createdAt     Creation timestamp
     * @param updatedAt     Last updated timestamp
     * @return Reconstructed Account aggregate
     */
    public static Account reconstruct(UUID accountId,
                                      AccountNumber accountNumber,
                                      UUID customerId,
                                      Money balance,
                                      AccountStatus status,
                                      LocalDateTime createdAt,
                                      LocalDateTime updatedAt) {
        return new Account(accountId, accountNumber, customerId, balance, status, createdAt, updatedAt);
    }

    /**
     * Change the account status.
     *
     * @param newStatus New status to set
     * @param clock     Clock instance for timestamping
     */
    public void changeStatus(AccountStatus newStatus, Clock clock) {
        if (this.status == newStatus) {
            throw new IllegalStateException("Account is already in status: " + newStatus);
        }
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now(clock);
    }

    /**
     * Deposit money into the account.
     *
     * @param amount Money to deposit
     * @param clock  Clock instance for timestamping
     */
    public void deposit(Money amount, Clock clock) {
        if (amount == null || amount.getAmount().signum() <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount);
        this.updatedAt = LocalDateTime.now(clock);
    }

    /**
     * Withdraw money from the account.
     *
     * @param amount Money to withdraw
     * @param clock  Clock instance for timestamping
     */
    public void withdraw(Money amount, Clock clock) {
        if (amount == null || amount.getAmount().signum() <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (this.balance.isLessThan(amount)) {
            throw new IllegalStateException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
        this.updatedAt = LocalDateTime.now(clock);
    }

    // Getters

    public UUID getAccountId() {
        return accountId;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Money getBalance() {
        return balance;
    }

    public String getCurrencyCode() {return balance.getCurrencyCode();}

    public AccountStatus getStatus() {return status;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return accountId.equals(account.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}

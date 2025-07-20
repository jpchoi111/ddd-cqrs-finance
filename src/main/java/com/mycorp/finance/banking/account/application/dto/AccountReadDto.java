package com.mycorp.finance.banking.account.application.dto;

import com.mycorp.finance.banking.account.domain.model.enums.AccountStatus;
import com.mycorp.finance.banking.account.infrastructure.persistence.entity.query.AccountReadEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for read-only Account data.
 * Immutable and designed for safe data exposure to clients.
 */
public class AccountReadDto {

    private final UUID accountId;
    private final String accountNumber;
    private final long balanceAmount; // minor units like cents
    private final String currencyCode; // ISO currency code
    private final AccountStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private AccountReadDto(UUID accountId,
                           String accountNumber,
                           long balanceAmount,
                           String currencyCode,
                           AccountStatus status,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balanceAmount = balanceAmount;
        this.currencyCode = currencyCode;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public long getBalanceAmount() {
        return balanceAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Factory method to create DTO from AccountReadEntity.
     *
     * @param entity AccountReadEntity from DB
     * @return AccountReadDto immutable instance
     */
    public static AccountReadDto from(AccountReadEntity entity) {
        return new AccountReadDto(
                entity.getId(),
                entity.getAccountNumber(),
                entity.getBalanceAmount(),
                entity.getCurrencyCode(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

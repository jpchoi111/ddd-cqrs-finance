package com.mycorp.finance.banking.transaction.domain.model;

import com.mycorp.finance.banking.common.AccountNumber;
import com.mycorp.finance.banking.common.Money;
import com.mycorp.finance.banking.transaction.domain.model.enums.TransactionStatus;
import com.mycorp.finance.banking.transaction.domain.model.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a financial transaction between accounts.
 * This is a domain entity that encapsulates business rules
 * related to fund transfers, deposits, and withdrawals.
 */
public class Transaction {

    private final String id; // UUID, immutable
    private final AccountNumber sourceAccount; // nullable for DEPOSIT
    private final AccountNumber targetAccount;
    private final Money amount;
    private final TransactionType type;
    private TransactionStatus status;
    private final LocalDateTime occurredAt;

    /**
     * Constructs a new Transaction instance.
     * Private constructor to enforce factory method usage.
     */
    private Transaction(
            String id,
            AccountNumber sourceAccount,
            AccountNumber targetAccount,
            Money amount,
            TransactionType type,
            TransactionStatus status,
            LocalDateTime occurredAt
    ) {
        this.id = id;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.occurredAt = occurredAt;
    }

    /**
     * Factory method for creating a transfer transaction.
     */
    public static Transaction createTransfer(
            AccountNumber from,
            AccountNumber to,
            Money amount,
            LocalDateTime now
    ) {
        Objects.requireNonNull(from, "source account must not be null");
        Objects.requireNonNull(to, "target account must not be null");

        return new Transaction(
                UUID.randomUUID().toString(),
                from,
                to,
                amount,
                TransactionType.TRANSFER,
                TransactionStatus.PENDING,
                now
        );
    }

    /**
     * Factory method for creating a deposit transaction.
     */
    public static Transaction createDeposit(
            AccountNumber to,
            Money amount,
            LocalDateTime now
    ) {
        Objects.requireNonNull(to, "target account must not be null");

        return new Transaction(
                UUID.randomUUID().toString(),
                null,
                to,
                amount,
                TransactionType.DEPOSIT,
                TransactionStatus.PENDING,
                now
        );
    }

    /**
     * Factory method for creating a withdrawal transaction.
     */
    public static Transaction createWithdrawal(
            AccountNumber from,
            Money amount,
            LocalDateTime now
    ) {
        Objects.requireNonNull(from, "source account must not be null");

        return new Transaction(
                UUID.randomUUID().toString(),
                from,
                null,
                amount,
                TransactionType.WITHDRAWAL,
                TransactionStatus.PENDING,
                now
        );
    }

    /**
     * Marks this transaction as successfully completed.
     */
    public void markCompleted() {
        this.status = TransactionStatus.COMPLETED;
    }

    /**
     * Marks this transaction as failed.
     */
    public void markFailed() {
        this.status = TransactionStatus.FAILED;
    }

    // === Getters ===

    public String getId() {
        return id;
    }

    public AccountNumber getSourceAccount() {
        return sourceAccount;
    }

    public AccountNumber getTargetAccount() {
        return targetAccount;
    }

    public Money getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    // === Equality based on ID ===

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

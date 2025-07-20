package com.mycorp.finance.banking.account.infrastructure.persistence.entity.query;

import com.mycorp.finance.banking.account.domain.model.enums.AccountStatus;
import com.mycorp.finance.banking.account.infrastructure.messaging.schema.AccountCreatedEvent;
import com.mycorp.finance.banking.account.infrastructure.messaging.schema.AccountUpdatedEvent;
import com.mycorp.finance.global.base.BaseEntity;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Read-side projection entity for Account data.
 * Used in CQRS architecture to store denormalized account data for queries.
 */
@Entity
@Table(name = "account_read")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountReadEntity extends BaseEntity {

    @Column(name = "account_number", length = 20, nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "balance_amount", nullable = false)
    private Long balanceAmount;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private AccountStatus status;

    private AccountReadEntity(UUID id, String accountNumber, Long balanceAmount, String currencyCode, AccountStatus status) {
        super(id);
        this.accountNumber = accountNumber;
        this.balanceAmount = balanceAmount;
        this.currencyCode = currencyCode;
        this.status = status;
    }

    /**
     * Creates an AccountReadEntity from AccountCreatedEvent.
     *
     * @param event AccountCreatedEvent from Kafka
     * @return new AccountReadEntity populated from event
     */
    public static AccountReadEntity from(AccountCreatedEvent event) {
        return new AccountReadEntity(
                UUID.fromString(event.getAccountId()),
                event.getAccountNumber(),
                toMinorUnits(event.getInitialBalance()),
                event.getCurrency(),
                AccountStatus.valueOf(event.getStatus())
        );
    }

    /**
     * Creates a new AccountReadEntity with updated values from AccountUpdatedEvent.
     * This preserves immutability by returning a new instance.
     *
     * @param event AccountUpdatedEvent from Kafka
     * @return updated AccountReadEntity instance
     */
    public AccountReadEntity withUpdated(AccountUpdatedEvent event) {
        return new AccountReadEntity(
                this.getId(),
                this.accountNumber,
                toMinorUnits(event.getBalance()),
                this.currencyCode,
                AccountStatus.valueOf(event.getStatus())
        );
    }

    /**
     * Converts a balance in major units (e.g., dollars) to minor units (e.g., cents).
     *
     * @param amount balance in double
     * @return balance in long (minor units)
     */
    private static long toMinorUnits(double amount) {
        return Math.round(amount * 100);
    }
}

package com.mycorp.finance.banking.account.infrastructure.messaging;

import com.mycorp.finance.banking.account.domain.model.Account;
import com.mycorp.finance.banking.account.infrastructure.messaging.schema.AccountCreatedEvent;
import com.mycorp.finance.banking.account.infrastructure.messaging.schema.AccountUpdatedEvent;
import com.mycorp.finance.banking.account.infrastructure.messaging.schema.AccountDeletedEvent;
import org.springframework.stereotype.Component;

import java.time.Clock;

/**
 * Maps Account domain model to Avro-based event schemas.
 * Injected Clock ensures consistent timestamp generation.
 */
@Component
public class AccountEventMapper {

    private final Clock clock;

    public AccountEventMapper(Clock clock) {
        this.clock = clock;
    }

    /**
     * Maps Account to AccountCreatedEvent Avro schema.
     */
    public AccountCreatedEvent toAccountCreatedEvent(Account account) {
        return AccountCreatedEvent.newBuilder()
                .setAccountId(account.getAccountId().toString())
                .setAccountNumber(account.getAccountNumber().value())
                .setCustomerId(account.getCustomerId().toString())
                .setInitialBalance(account.getBalance().getAmount().doubleValue())
                .setCurrency(account.getCurrencyCode())
                .setStatus(account.getStatus().name())
                .setCreatedAt(account.getCreatedAt().atZone(clock.getZone()).toInstant().toEpochMilli())
                .build();
    }

    /**
     * Maps Account to AccountUpdatedEvent Avro schema.
     */
    public AccountUpdatedEvent toAccountUpdatedEvent(Account account) {
        return AccountUpdatedEvent.newBuilder()
                .setAccountId(account.getAccountId().toString())
                .setAccountNumber(account.getAccountNumber().value())
                .setCustomerId(account.getCustomerId().toString())
                .setBalance(account.getBalance().getAmount().doubleValue())
                .setCurrency(account.getCurrencyCode())
                .setStatus(account.getStatus().name())
                .setUpdatedAt(account.getUpdatedAt().atZone(clock.getZone()).toInstant().toEpochMilli())
                .build();
    }

    /**
     * Maps Account to AccountDeletedEvent Avro schema.
     */
    public AccountDeletedEvent toAccountDeletedEvent(Account account) {
        return AccountDeletedEvent.newBuilder()
                .setAccountId(account.getAccountId().toString())
                .setDeletedAt(clock.instant().toEpochMilli())
                .build();
    }
}

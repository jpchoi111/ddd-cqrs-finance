package com.mycorp.finance.banking.account.application.handler;

import com.mycorp.finance.banking.account.infrastructure.messaging.schema.*;
import com.mycorp.finance.banking.account.infrastructure.persistence.entity.query.AccountReadEntity;
import com.mycorp.finance.banking.account.infrastructure.persistence.repository.query.AccountQueryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Handler for processing Kafka account events and updating the query projection.
 * Part of the CQRS query-side update mechanism.
 */
@Component
@RequiredArgsConstructor
public class AccountEventHandler {
    private final AccountQueryJpaRepository accountQueryJpaRepository;

    public void handle(AccountCreatedEvent event) {
        AccountReadEntity entity = AccountReadEntity.from(event);
        accountQueryJpaRepository.save(entity);
    }

    public void handle(AccountUpdatedEvent event) {
        accountQueryJpaRepository.findById(UUID.fromString(event.getAccountId()))
                .ifPresent(existing -> {
                    AccountReadEntity updated = existing.withUpdated(event);
                    accountQueryJpaRepository.save(updated);
                });
    }

    public void handle(AccountDeletedEvent event) {
        accountQueryJpaRepository.deleteById(UUID.fromString(event.getAccountId()));
    }
}

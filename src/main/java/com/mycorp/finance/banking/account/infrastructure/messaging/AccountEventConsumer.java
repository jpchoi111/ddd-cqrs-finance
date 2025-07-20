package com.mycorp.finance.banking.account.infrastructure.messaging;

import com.mycorp.finance.banking.account.application.handler.AccountEventHandler;
import com.mycorp.finance.banking.account.infrastructure.messaging.schema.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for account domain events.
 * Delegates actual processing to AccountEventHandler.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountEventConsumer {

    private final AccountEventHandler handler;

    @KafkaListener(
            topics = "${spring.kafka.topics.account-created}",
            groupId = "${spring.kafka.consumer-groups.account}"
    )
    public void onAccountCreated(AccountCreatedEvent event) {
        log.info("Received AccountCreatedEvent: {}", event);
        handler.handle(event);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.account-updated}",
            groupId = "${spring.kafka.consumer-groups.account}"
    )
    public void onAccountUpdated(AccountUpdatedEvent event) {
        log.info("Received AccountUpdatedEvent: {}", event);
        handler.handle(event);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.account-deleted}",
            groupId = "${spring.kafka.consumer-groups.account}"
    )
    public void onAccountDeleted(AccountDeletedEvent event) {
        log.info("Received AccountDeletedEvent: {}", event);
        handler.handle(event);
    }
}

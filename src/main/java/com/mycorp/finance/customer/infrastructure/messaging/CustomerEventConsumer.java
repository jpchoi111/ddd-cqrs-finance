package com.mycorp.finance.customer.infrastructure.messaging;

import com.mycorp.finance.customer.application.handler.CustomerEventHandler;
import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerCreatedEvent;
import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerUpdatedEvent;
import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for customer domain events.
 * Delegates event processing to CustomerEventHandler.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerEventConsumer {

    private final CustomerEventHandler handler;

    @KafkaListener(
            topics = "${spring.kafka.topics.customer-created}",
            groupId = "${spring.kafka.consumer-groups.customer}"
    )
    public void onCustomerCreated(CustomerCreatedEvent event) {
        log.info("Received CustomerCreatedEvent: {}", event);
        handler.handle(event);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.customer-updated}",
            groupId = "${spring.kafka.consumer-groups.customer}"
    )
    public void onCustomerUpdated(CustomerUpdatedEvent event) {
        log.info("Received CustomerUpdatedEvent: {}", event);
        handler.handle(event);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.customer-deleted}",
            groupId = "${spring.kafka.consumer-groups.customer}"
    )
    public void onCustomerDeleted(CustomerDeletedEvent event) {
        log.info("Received CustomerDeletedEvent: {}", event);
        handler.handle(event);
    }
}

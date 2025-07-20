package com.mycorp.finance.customer.application.handler;

import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerCreatedEvent;
import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerDeletedEvent;
import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerUpdatedEvent;
import com.mycorp.finance.customer.infrastructure.persistence.entity.query.CustomerReadEntity;
import com.mycorp.finance.customer.infrastructure.persistence.mapper.CustomerReadMapper;
import com.mycorp.finance.customer.infrastructure.persistence.repository.query.CustomerQueryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Handles customer domain events received from Kafka.
 * Responsible for creating or updating the customer read model (CQRS read side).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerEventHandler {

    private final CustomerQueryJpaRepository repository;
    private final CustomerReadMapper mapper;

    /**
     * Handles customer creation event by creating a new read model entity.
     *
     * @param event CustomerCreatedEvent from Kafka
     */
    @Transactional
    public void handle(CustomerCreatedEvent event) {
        log.info("Handling CustomerCreatedEvent: {}", event);

        if (repository.existsByEmail(event.getEmail())) {
            log.warn("Customer with email {} already exists in read model.", event.getEmail());
            return;
        }

        CustomerReadEntity entity = mapper.toEntity(event);
        repository.save(entity);
    }

    /**
     * Handles customer update event by applying changes to existing read model entity.
     *
     * @param event CustomerUpdatedEvent from Kafka
     */
    @Transactional
    public void handle(CustomerUpdatedEvent event) {
        log.info("Handling CustomerUpdatedEvent: {}", event);

        UUID customerId = UUID.fromString(event.getCustomerId());
        CustomerReadEntity current = repository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("Customer not found in read model: " + customerId));

        CustomerReadEntity updated = mapper.applyUpdate(current, event);
        repository.save(updated);
    }

    /**
     * Handles customer deletion by removing the entity from the read model.
     *
     * @param event CustomerDeletedEvent from Kafka
     */
    @Transactional
    public void handle(CustomerDeletedEvent event) {
        log.info("Handling CustomerDeletedEvent: {}", event);

        UUID customerId = UUID.fromString(event.getCustomerId());
        repository.deleteById(customerId);
    }
}

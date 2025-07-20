package com.mycorp.finance.customer.infrastructure.persistence.mapper;

import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerCreatedEvent;
import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerUpdatedEvent;
import com.mycorp.finance.customer.infrastructure.persistence.entity.query.CustomerReadEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert Kafka customer events into CustomerReadEntity instances.
 * Used in the query-side projection (CQRS).
 */
@Component
public class CustomerReadMapper {

    /**
     * Converts a CustomerCreatedEvent into a new CustomerReadEntity.
     *
     * @param event the event containing initial customer data
     * @return a new read model entity
     */
    public CustomerReadEntity toEntity(CustomerCreatedEvent event) {
        return CustomerReadEntity.from(event);
    }

    /**
     * Produces a new updated CustomerReadEntity from an update event.
     * This ensures immutability by returning a new instance.
     *
     * @param current the existing read entity from DB
     * @param event   the update event
     * @return updated CustomerReadEntity instance
     */
    public CustomerReadEntity applyUpdate(CustomerReadEntity current, CustomerUpdatedEvent event) {
        return current.withUpdated(event);
    }
}

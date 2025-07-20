package com.mycorp.finance.customer.infrastructure.messaging;

import com.mycorp.finance.customer.domain.model.Customer;
import com.mycorp.finance.customer.infrastructure.messaging.schema.*;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.format.DateTimeFormatter;

/**
 * Maps Customer domain model to various Avro-based event schemas.
 * This unified mapper handles creation, update, and deletion mappings.
 */
@Component
public final class CustomerEventMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final Clock clock;

    public CustomerEventMapper(Clock clock) {
        this.clock = clock;
    }

    /**
     * Maps Customer domain model to CustomerCreatedEvent Avro schema.
     */
    public CustomerCreatedEvent toCustomerCreatedEvent(Customer customer) {
        return CustomerCreatedEvent.newBuilder()
                .setCustomerId(customer.getId().toString())
                .setEmail(customer.getEmail().toString())
                .setName(toName(customer))
                .setBirthDate(customer.getBirthDate().toString())
                .setPhoneNumber(customer.getPhoneNumber().getValue())
                .setAddress(toAddress(customer))
                .setStatus(customer.getStatus().name())
                .setCreatedAt(currentTimestamp())
                .build();
    }

    /**
     * Maps Customer domain model to CustomerUpdatedEvent Avro schema.
     */
    public CustomerUpdatedEvent toCustomerUpdatedEvent(Customer customer) {
        return CustomerUpdatedEvent.newBuilder()
                .setCustomerId(customer.getId().toString())
                .setName(toName(customer))
                .setPhoneNumber(customer.getPhoneNumber().getValue())
                .setAddress(toAddress(customer))
                .setStatus(customer.getStatus().name())
                .setUpdatedAt(currentTimestamp())
                .build();
    }

    /**
     * Maps Customer domain model to CustomerDeletedEvent Avro schema.
     */
    public CustomerDeletedEvent toCustomerDeletedEvent(Customer customer) {
        return CustomerDeletedEvent.newBuilder()
                .setCustomerId(customer.getId().toString())
                .setDeletedAt(currentTimestamp())
                .build();
    }

    private Name toName(Customer customer) {
        Name name = new Name();
        name.setFirstName(customer.getName().getFirstName());
        name.setMiddleName(customer.getName().getMiddleName());
        name.setLastName(customer.getName().getLastName());
        return name;
    }

    private Address toAddress(Customer customer) {
        Address address = new Address();
        address.setStreet(customer.getAddress().getStreet());
        address.setCity(customer.getAddress().getCity());
        address.setState(customer.getAddress().getState());
        address.setPostalCode(customer.getAddress().getPostalCode());
        address.setCountry(customer.getAddress().getCountry());
        return address;
    }

    private String currentTimestamp() {
        return FORMATTER.format(clock.instant().atZone(clock.getZone()).toLocalDateTime());
    }
}

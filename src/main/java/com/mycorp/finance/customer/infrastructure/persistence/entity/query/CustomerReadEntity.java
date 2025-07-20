package com.mycorp.finance.customer.infrastructure.persistence.entity.query;

import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerCreatedEvent;
import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerUpdatedEvent;
import com.mycorp.finance.customer.infrastructure.messaging.schema.Name;
import com.mycorp.finance.customer.infrastructure.messaging.schema.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Read-side projection entity for Customer data.
 * <p>
 * - Built from Kafka events (CQRS read model)
 * - No setter used; update via domain methods for immutability
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "customer_read")
public class CustomerReadEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private CustomerReadEntity(
            UUID id,
            String name,
            String email,
            String address,
            String phoneNumber,
            LocalDate birthDate
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    /**
     * Static factory method to construct from CustomerCreatedEvent.
     */
    public static CustomerReadEntity from(CustomerCreatedEvent event) {
        return new CustomerReadEntity(
                UUID.fromString(event.getCustomerId()),
                formatName(event.getName()),
                event.getEmail(),
                formatAddress(event.getAddress()),
                event.getPhoneNumber(),
                LocalDate.parse(event.getBirthDate(), DateTimeFormatter.ISO_DATE)
        );
    }

    /**
     * Returns a new instance with updated values from CustomerUpdatedEvent.
     */
    public CustomerReadEntity withUpdated(CustomerUpdatedEvent event) {
        return new CustomerReadEntity(
                this.id,
                formatName(event.getName()),
                this.email, // email is immutable
                formatAddress(event.getAddress()),
                event.getPhoneNumber(),
                this.birthDate // birthDate is immutable
        );
    }

    private static String formatName(Name name) {
        return name.getLastName() + " " +
                (name.getMiddleName() != null ? name.getMiddleName() + " " : "") +
                name.getFirstName();
    }

    private static String formatAddress(Address address) {
        return String.join(", ",
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry());
    }
}

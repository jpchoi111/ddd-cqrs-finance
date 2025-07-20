package com.mycorp.finance.customer.infrastructure.messaging;


import com.mycorp.finance.customer.domain.event.CustomerEventPublisher;
import com.mycorp.finance.customer.domain.model.Customer;
import com.mycorp.finance.customer.infrastructure.messaging.CustomerEventMapper;
import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerCreatedEvent;
import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerDeletedEvent;
import com.mycorp.finance.customer.infrastructure.messaging.schema.CustomerUpdatedEvent;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Infrastructure implementation of CustomerEventPublisher.
 * Responsible for mapping domain objects to Avro events and sending them to Kafka.
 */
@Component
public class CustomerEventPublisherImpl implements CustomerEventPublisher {

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;
    private final CustomerEventMapper eventMapper;

    @Value("${spring.kafka.topics.customer-created}")
    private String customerCreatedTopic;

    @Value("${spring.kafka.topics.customer-updated}")
    private String customerUpdatedTopic;

    @Value("${spring.kafka.topics.customer-deleted}")
    private String customerDeletedTopic;

    public CustomerEventPublisherImpl(
            KafkaTemplate<String, SpecificRecord> kafkaTemplate,
            CustomerEventMapper eventMapper
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.eventMapper = eventMapper;
    }

    @Override
    public void publishCustomerCreated(Customer customer) {
        CustomerCreatedEvent event = eventMapper.toCustomerCreatedEvent(customer);
        kafkaTemplate.send(customerCreatedTopic, event.getCustomerId(), event);
    }

    @Override
    public void publishCustomerUpdated(Customer customer) {
        CustomerUpdatedEvent event = eventMapper.toCustomerUpdatedEvent(customer);
        kafkaTemplate.send(customerUpdatedTopic, event.getCustomerId(), event);
    }

    @Override
    public void publishCustomerDeleted(Customer customer) {
        CustomerDeletedEvent event = eventMapper.toCustomerDeletedEvent(customer);
        kafkaTemplate.send(customerDeletedTopic, event.getCustomerId(), event);
    }
}

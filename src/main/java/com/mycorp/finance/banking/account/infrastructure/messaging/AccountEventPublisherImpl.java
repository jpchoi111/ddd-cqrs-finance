package com.mycorp.finance.banking.account.infrastructure.messaging;

import com.mycorp.finance.banking.account.domain.event.AccountEventPublisher;
import com.mycorp.finance.banking.account.domain.model.Account;
import com.mycorp.finance.banking.account.infrastructure.messaging.schema.AccountCreatedEvent;
import com.mycorp.finance.banking.account.infrastructure.messaging.schema.AccountUpdatedEvent;
import com.mycorp.finance.banking.account.infrastructure.messaging.schema.AccountDeletedEvent;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Implementation of AccountEventPublisher that publishes Avro events via Kafka.
 */
@Component
public class AccountEventPublisherImpl implements AccountEventPublisher {

    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;
    private final AccountEventMapper mapper;

    @Value("${spring.kafka.topics.account-created}")
    private String accountCreatedTopic;

    @Value("${spring.kafka.topics.account-updated}")
    private String accountUpdatedTopic;

    @Value("${spring.kafka.topics.account-deleted}")
    private String accountDeletedTopic;


    public AccountEventPublisherImpl(KafkaTemplate<String, SpecificRecord> kafkaTemplate, AccountEventMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    @Override
    public void publishAccountCreated(Account account) {
        AccountCreatedEvent event = mapper.toAccountCreatedEvent(account);
        kafkaTemplate.send(accountCreatedTopic, account.getAccountId().toString(), event);
    }

    @Override
    public void publishAccountUpdated(Account account) {
        AccountUpdatedEvent event = mapper.toAccountUpdatedEvent(account);
        kafkaTemplate.send(accountUpdatedTopic, account.getAccountId().toString(), event);
    }

    @Override
    public void publishAccountDeleted(Account account) {
        AccountDeletedEvent event = mapper.toAccountDeletedEvent(account);
        kafkaTemplate.send(accountDeletedTopic, account.getAccountId().toString(), event);
    }
}

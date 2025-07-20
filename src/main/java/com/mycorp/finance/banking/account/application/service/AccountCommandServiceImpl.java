package com.mycorp.finance.banking.account.application.service;

import com.mycorp.finance.banking.account.application.dto.AccountCreateCommand;
import com.mycorp.finance.banking.account.application.dto.AccountUpdateCommand;
import com.mycorp.finance.banking.account.domain.model.Account;
import com.mycorp.finance.banking.account.domain.model.enums.AccountStatus;
import com.mycorp.finance.banking.account.domain.repository.AccountCommandRepository;
import com.mycorp.finance.banking.account.domain.service.AccountValidationService;
import com.mycorp.finance.banking.account.domain.event.AccountEventPublisher;
import com.mycorp.finance.banking.common.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

/**
 * Application service handling account-related commands.
 * Applies business validation and emits domain events upon state changes.
 * Injects Clock for consistent time management.
 */
@Service
@Transactional
public class AccountCommandServiceImpl implements AccountCommandService {

    private final AccountCommandRepository accountCommandRepository;
    private final AccountValidationService accountValidationService;
    private final AccountEventPublisher eventPublisher;
    private final Clock clock;

    public AccountCommandServiceImpl(
            AccountCommandRepository accountCommandRepository,
            AccountValidationService accountValidationService,
            AccountEventPublisher eventPublisher,
            Clock clock
    ) {
        this.accountCommandRepository = accountCommandRepository;
        this.accountValidationService = accountValidationService;
        this.eventPublisher = eventPublisher;
        this.clock = clock;
    }

    /**
     * Creates a new account by applying domain rules and emitting a domain event.
     *
     * @param command DTO with initial account creation data
     */
    @Override
    public void createAccount(AccountCreateCommand command) {
        accountValidationService.validateForCreate(command);

        Account account = Account.createNewAccount(
                command.customerId(),
                Money.of(command.initialBalance(), command.currency()),
                clock
        );

        accountCommandRepository.save(account);

        eventPublisher.publishAccountCreated(account);
    }

    /**
     * Updates an account's status after verifying business rules and emits an update event.
     *
     * @param command DTO with status update info
     */
    @Override
    public void updateAccount(AccountUpdateCommand command) {
        Account account = accountCommandRepository.findById(command.accountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + command.accountId()));

        AccountStatus newStatus = AccountStatus.valueOf(command.status());

        switch (newStatus) {
            case CLOSED -> accountValidationService.validateClose(account);
            case DORMANT -> accountValidationService.validateDormant(account);
            case ACTIVE -> accountValidationService.validateReactivate(account);
            default -> {} // no-op
        }

        account.changeStatus(newStatus, clock);

        accountCommandRepository.save(account);

        eventPublisher.publishAccountUpdated(account);
    }
}

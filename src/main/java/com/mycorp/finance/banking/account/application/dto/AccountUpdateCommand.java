package com.mycorp.finance.banking.account.application.dto;

import java.util.UUID;

/**
 * Command DTO used to request an update to an existing Account aggregate.
 * Typically used to trigger a state change such as updating the account status.
 * Immutable and designed for write-only operations within the command model.
 */
public record AccountUpdateCommand(
        UUID accountId,
        String status
) {}

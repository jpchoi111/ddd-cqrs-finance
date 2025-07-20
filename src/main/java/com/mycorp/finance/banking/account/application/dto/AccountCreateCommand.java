package com.mycorp.finance.banking.account.application.dto;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

/**
 * Command DTO used to initiate the creation of a new Account aggregate.
 * This object encapsulates the input parameters necessary for account creation.
 * Immutable and intended for use in the write side of the application (CQRS).
 */
public record AccountCreateCommand(
        UUID customerId,
        BigDecimal initialBalance,
        Currency currency
) {}

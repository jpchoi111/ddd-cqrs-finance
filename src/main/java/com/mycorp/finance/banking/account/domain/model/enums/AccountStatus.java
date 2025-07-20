package com.mycorp.finance.banking.account.domain.model.enums;

/**
 * Enum representing the various possible statuses of a bank account.
 * This helps track the state of the account, which influences its behavior.
 */
public enum AccountStatus {

    ACTIVE,       // Account is active and in good standing.
    INACTIVE,     // Account is inactive; no operations are allowed.
    CLOSED,       // Account is closed, no further transactions are allowed.
    SUSPENDED,    // Account is suspended; cannot perform any transactions.
    PENDING,      // Account creation is pending, and no transactions are allowed.
    FROZEN,       // Account is frozen due to legal or regulatory reasons.
    DORMANT       // Account is dormant due to prolonged inactivity.
}

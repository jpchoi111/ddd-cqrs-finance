package com.mycorp.finance.customer.domain.model.enums;

/**
 * Enum representing the status of a Customer.
 */
public enum CustomerStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED, // e.g. for compliance or fraud reasons
    CLOSED
}


package com.mycorp.finance.customer.domain.event;

import com.mycorp.finance.customer.domain.model.Customer;

/**
 * Abstraction for publishing customer-related events.
 * Infrastructure implementation should handle actual publishing logic.
 */
public interface CustomerEventPublisher {

    void publishCustomerCreated(Customer customer);

    void publishCustomerUpdated(Customer customer);

    void publishCustomerDeleted(Customer customer);
}

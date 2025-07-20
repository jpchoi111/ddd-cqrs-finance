package com.mycorp.finance.global.base;

import java.util.UUID;

/**
 * Generic aggregate root base class.
 */
public abstract class AggregateRoot<T> {

    protected final T id;

    protected AggregateRoot(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}

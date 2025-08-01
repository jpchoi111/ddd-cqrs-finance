package com.mycorp.finance.banking.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * Embeddable Value Object representing monetary amount and currency.
 * Used as a component inside JPA entities.
 */
@Embeddable
@Getter
public class Money {

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currencyCode;

    protected Money() {
        // JPA requires protected no-args constructor
    }

    /**
     * Constructs Money instance with amount and currency.
     * Amount is scaled to 2 decimal places with HALF_EVEN rounding.
     *
     * @param amount   monetary amount, must not be null
     * @param currency currency, must not be null
     */
    public Money(BigDecimal amount, Currency currency) {
        if (amount == null || currency == null) {
            throw new IllegalArgumentException("Amount and currency must not be null");
        }
        this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
        //this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.currencyCode = currency.getCurrencyCode();
    }

    /**
     * Static factory method to create Money instance.
     *
     * @param amount   monetary amount, must not be null
     * @param currency currency, must not be null
     * @return new Money instance
     */
    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public Currency getCurrency() {
        return Currency.getInstance(currencyCode);
    }

    public Money add(Money other) {
        validateCurrency(other);
        return new Money(this.amount.add(other.amount), getCurrency());
    }

    public Money subtract(Money other) {
        validateCurrency(other);
        return new Money(this.amount.subtract(other.amount), getCurrency());
    }

    private void validateCurrency(Money other) {
        if (!this.currencyCode.equals(other.currencyCode)) {
            throw new IllegalArgumentException("Currency mismatch: " + this.currencyCode + " vs " + other.currencyCode);
        }
    }

    public boolean isLessThan(Money other) {
        validateCurrency(other);
        return this.amount.compareTo(other.amount) < 0;
    }

    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isPositive() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0 &&
                currencyCode.equals(money.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount.stripTrailingZeros(), currencyCode);
    }

    @Override
    public String toString() {
        return amount + " " + currencyCode;
    }
}

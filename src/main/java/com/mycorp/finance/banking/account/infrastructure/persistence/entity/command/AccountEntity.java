package com.mycorp.finance.banking.account.infrastructure.persistence.entity.command;

import com.mycorp.finance.banking.account.domain.model.enums.AccountStatus;
import com.mycorp.finance.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * JPA Entity representing a Bank Account.
 * Inherits from BaseEntity for id and audit timestamp fields.
 */
@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA requires a no-arg constructor, protected for encapsulation
public class AccountEntity extends BaseEntity {

    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "balance_amount", nullable = false)
    private Long balanceAmount; // Stored as minor unit, e.g. cents

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode; // Example: "USD"

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AccountStatus status;

    /**
     * Protected constructor to restrict direct instantiation.
     * Use static factory method {@link #of(UUID, String, UUID, Long, String, AccountStatus)} instead.
     */
    protected AccountEntity(UUID id,
                            String accountNumber,
                            UUID customerId,
                            Long balanceAmount,
                            String currencyCode,
                            AccountStatus status) {
        super(id);
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balanceAmount = balanceAmount;
        this.currencyCode = currencyCode;
        this.status = status;
    }

    /**
     * Static factory method to create a new AccountEntity instance.
     *
     * @param id            Unique account ID
     * @param accountNumber Unique account number
     * @param customerId    ID of the customer owning the account
     * @param balanceAmount Account balance in minor units (e.g. cents)
     * @param currencyCode  3-letter ISO currency code (e.g. "USD")
     * @param status        Current account status
     * @return new AccountEntity instance
     */
    public static AccountEntity of(UUID id,
                                   String accountNumber,
                                   UUID customerId,
                                   Long balanceAmount,
                                   String currencyCode,
                                   AccountStatus status) {
        return new AccountEntity(id, accountNumber, customerId, balanceAmount, currencyCode, status);
    }
}

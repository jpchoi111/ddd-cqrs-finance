package com.mycorp.finance.banking.account.infrastructure.persistence.mapper;

import com.mycorp.finance.banking.account.domain.model.Account;
import com.mycorp.finance.banking.account.infrastructure.persistence.entity.command.AccountEntity;
import com.mycorp.finance.banking.common.AccountNumber;
import com.mycorp.finance.banking.common.Money;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Mapper for converting between Account domain model and AccountEntity persistence model.
 * Designed for immutability and straightforward conversions.
 */
public class AccountMapper {

    /**
     * Converts Account domain aggregate to JPA entity.
     *
     * @param account Domain aggregate root instance
     * @return AccountEntity ready for persistence
     */
    public static AccountEntity toEntity(Account account) {
        if (account == null) {
            return null;
        }

        long balanceAmount = account.getBalance()
                .getAmount()
                .multiply(BigDecimal.valueOf(100)) // assuming minor unit is cent
                .longValue();

        return AccountEntity.of(
                account.getAccountId(),
                account.getAccountNumber().value(),
                account.getCustomerId(),
                balanceAmount,
                account.getBalance().getCurrency().getCurrencyCode(),
                account.getStatus()
        );
    }

    /**
     * Converts AccountEntity to domain aggregate.
     *
     * @param entity persistence entity
     * @return reconstructed Account aggregate root
     */
    public static Account toDomain(AccountEntity entity) {
        if (entity == null) {
            return null;
        }

        Money balance = new Money(
                BigDecimal.valueOf(entity.getBalanceAmount()).divide(BigDecimal.valueOf(100)),
                Currency.getInstance(entity.getCurrencyCode())
        );

        return Account.reconstruct(
                entity.getId(),
                AccountNumber.of(entity.getAccountNumber()),
                entity.getCustomerId(),
                balance,
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

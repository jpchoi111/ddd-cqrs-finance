package com.mycorp.finance.banking.account.infrastructure.persistence.adapter;

import com.mycorp.finance.banking.account.application.dto.AccountReadDto;
import com.mycorp.finance.banking.account.domain.repository.AccountQueryRepository;
import com.mycorp.finance.banking.common.AccountNumber;
import com.mycorp.finance.banking.account.infrastructure.persistence.repository.query.AccountQueryJpaRepository;
import com.mycorp.finance.banking.account.infrastructure.persistence.mapper.AccountReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Adapter implementation of AccountQueryRepository using Spring Data JPA.
 * Converts read entities into DTOs for safe exposure to application layer.
 */
@Repository
@Transactional("queryTransactionManager")
@RequiredArgsConstructor
public class AccountQueryRepositoryImpl implements AccountQueryRepository {

    private final AccountQueryJpaRepository accountQueryJpaRepository;
    private final AccountReadMapper accountReadMapper;

    /**
     * Finds an account by its business identifier (accountNumber).
     *
     * @param accountNumber the account number value object
     * @return optional AccountReadDto if found
     */
    @Override
    public Optional<AccountReadDto> findByAccountNumber(AccountNumber accountNumber) {
        return accountQueryJpaRepository.findByAccountNumber(accountNumber.value())
                .map(accountReadMapper::toDto);
    }

    /**
     * Checks whether an account with the given number exists.
     *
     * @param accountNumber the account number
     * @return true if exists, false otherwise
     */
    @Override
    public boolean existsByAccountNumber(AccountNumber accountNumber) {
        return accountQueryJpaRepository.existsByAccountNumber(accountNumber.value());
    }
}


package com.mycorp.finance.banking.account.infrastructure.persistence.adapter;

import com.mycorp.finance.banking.account.domain.model.Account;
import com.mycorp.finance.banking.account.domain.repository.AccountCommandRepository;
import com.mycorp.finance.banking.account.infrastructure.persistence.entity.command.AccountEntity;
import com.mycorp.finance.banking.account.infrastructure.persistence.repository.command.AccountCommandJpaRepository;
import com.mycorp.finance.banking.account.infrastructure.persistence.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of AccountCommandRepository.
 * Responsible for handling command operations (create, update, delete) on Account aggregates.
 */
@Repository
@Transactional("commandTransactionManager")
@RequiredArgsConstructor
public class AccountCommandRepositoryImpl implements AccountCommandRepository {

    private final AccountCommandJpaRepository accountJpaRepository;

    @Override
    public Optional<Account> findById(UUID accountId) {
        return accountJpaRepository.findById(accountId)
                .map(AccountMapper::toDomain);
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = AccountMapper.toEntity(account);
        AccountEntity savedEntity = accountJpaRepository.save(entity);
        return AccountMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID accountId) {
        accountJpaRepository.deleteById(accountId);
    }
}

package com.mycorp.finance.banking.account.application.service;

import com.mycorp.finance.banking.account.application.dto.AccountReadDto;
import com.mycorp.finance.banking.account.domain.repository.AccountQueryRepository;
import com.mycorp.finance.banking.common.AccountNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of AccountQueryService.
 * Delegates data access to AccountQueryRepository.
 */
@Service
@RequiredArgsConstructor
public class AccountQueryServiceImpl implements AccountQueryService {

    private final AccountQueryRepository accountQueryRepository;

    @Override
    public Optional<AccountReadDto> findByAccountNumber(AccountNumber accountNumber) {
        return accountQueryRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public boolean existsByAccountNumber(AccountNumber accountNumber) {
        return accountQueryRepository.existsByAccountNumber(accountNumber);
    }
}

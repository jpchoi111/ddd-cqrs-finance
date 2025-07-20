package com.mycorp.finance.banking.account.domain.repository;

import com.mycorp.finance.banking.account.application.dto.AccountReadDto;
import com.mycorp.finance.banking.common.AccountNumber;

import java.util.Optional;

/**
 * Port interface for Account Query Repository.
 */
public interface AccountQueryRepository {

    Optional<AccountReadDto> findByAccountNumber(AccountNumber accountNumber);

    boolean existsByAccountNumber(AccountNumber accountNumber);
}

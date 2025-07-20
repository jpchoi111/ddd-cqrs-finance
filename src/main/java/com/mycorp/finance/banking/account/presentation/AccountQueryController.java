package com.mycorp.finance.banking.account.presentation;

import com.mycorp.finance.banking.account.application.dto.AccountReadDto;
import com.mycorp.finance.banking.account.application.service.AccountQueryService;
import com.mycorp.finance.banking.common.AccountNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST Controller for Account Query operations.
 * Handles read-only endpoints such as account lookup and existence check.
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountQueryController {

    private final AccountQueryService accountQueryService;

    /**
     * Retrieves account details by business identifier (accountNumber).
     *
     * @param accountNumber the account number string
     * @return HTTP 200 with account data if found, 404 otherwise
     */
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountReadDto> getAccountByNumber(@PathVariable String accountNumber) {
        Optional<AccountReadDto> accountOpt = accountQueryService.findByAccountNumber(AccountNumber.of(accountNumber));
        return accountOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Checks whether an account exists for the given account number.
     *
     * @param accountNumber the account number string
     * @return HTTP 200 with true/false
     */
    @GetMapping("/{accountNumber}/exists")
    public ResponseEntity<Boolean> checkExists(@PathVariable String accountNumber) {
        boolean exists = accountQueryService.existsByAccountNumber(AccountNumber.of(accountNumber));
        return ResponseEntity.ok(exists);
    }
}

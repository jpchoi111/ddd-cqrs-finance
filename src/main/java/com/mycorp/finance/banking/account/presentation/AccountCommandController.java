package com.mycorp.finance.banking.account.presentation;

import com.mycorp.finance.banking.account.application.dto.AccountCreateCommand;
import com.mycorp.finance.banking.account.application.dto.AccountUpdateCommand;
import com.mycorp.finance.banking.account.application.service.AccountCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

/**
 * REST controller for handling account command operations like creation and updates.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountCommandController {

    private final AccountCommandService accountCommandService;

    public AccountCommandController(AccountCommandService accountCommandService) {
        this.accountCommandService = accountCommandService;
    }

    /**
     * Endpoint to create a new account.
     *
     * @param request DTO with customerId, initialBalance, and currency info
     * @return HTTP 201 Created on success
     */
    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody AccountCreateRequest request) {
        // Convert request to command DTO

        var command = new AccountCreateCommand(
                request.customerId(),
                BigDecimal.valueOf(request.initialBalance()),
                Currency.getInstance(request.currency())
        );

        accountCommandService.createAccount(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Endpoint to update an account's status.
     *
     * @param accountId UUID of the account to update
     * @param request DTO with new status value
     * @return HTTP 204 No Content on success
     */
    @PutMapping("/{accountId}/status")
    public ResponseEntity<Void> updateAccountStatus(
            @PathVariable UUID accountId,
            @RequestBody AccountUpdateRequest request
    ) {
        var command = new AccountUpdateCommand(
                accountId,
                request.status()
        );

        accountCommandService.updateAccount(command);
        return ResponseEntity.noContent().build();
    }

    // --- DTO records for HTTP requests ---

    public record AccountCreateRequest(UUID customerId, double initialBalance, String currency) {}

    public record AccountUpdateRequest(String status) {}
}

package com.mycorp.finance.customer.presentation;

import com.mycorp.finance.customer.application.service.CustomerCommandService;
import com.mycorp.finance.customer.application.dto.CustomerRegisterCommand;
import com.mycorp.finance.customer.application.dto.CustomerUpdateCommand;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for handling commands related to Customer entity.
 * Responsible for write operations such as create, update, and delete.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerCommandController {

    private final CustomerCommandService customerCommandService;

    public CustomerCommandController(CustomerCommandService customerCommandService) {
        this.customerCommandService = customerCommandService;
    }

    /**
     * Registers a new customer.
     *
     * @param command Customer registration data
     * @return 201 Created on success
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerCustomer(@Valid @RequestBody CustomerRegisterCommand command) {
        customerCommandService.registerCustomer(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Updates an existing customer.
     *
     * @param customerId the ID of the customer to update
     * @param command Customer update data
     * @return 204 No Content on success
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<Void> updateCustomer(
            @PathVariable UUID customerId,
            @Valid @RequestBody CustomerUpdateCommand command) {

        customerCommandService.updateCustomer(customerId, command);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a customer by ID.
     *
     * @param customerId the ID of the customer to delete
     * @return 204 No Content on successful deletion
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID customerId) {
        customerCommandService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}


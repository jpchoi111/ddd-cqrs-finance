package com.mycorp.finance.customer.presentation;

import com.mycorp.finance.customer.application.service.CustomerQueryService;
import com.mycorp.finance.customer.application.dto.CustomerReadDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for querying customer data.
 * Responsible for read-only operations.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerQueryController {

    private final CustomerQueryService customerQueryService;

    public CustomerQueryController(CustomerQueryService customerQueryService) {
        this.customerQueryService = customerQueryService;
    }

    /**
     * Retrieves customer information by ID.
     *
     * @param customerId UUID
     * @return CustomerReadDto with 200 OK or 404 if not found
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerReadDto> getCustomerById(@PathVariable UUID customerId) {
        Optional<CustomerReadDto> customer = customerQueryService.getCustomerById(customerId);
        return customer
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}


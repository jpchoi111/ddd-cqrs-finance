package com.mycorp.finance.customer.application.service;

import com.mycorp.finance.customer.application.dto.CustomerRegisterCommand;
import com.mycorp.finance.customer.application.dto.CustomerUpdateCommand;
import com.mycorp.finance.customer.domain.model.Customer;
import com.mycorp.finance.customer.domain.model.vo.Email;
import com.mycorp.finance.customer.domain.model.vo.Password;
import com.mycorp.finance.customer.domain.repository.CustomerCommandRepository;
import com.mycorp.finance.customer.domain.service.CustomerDomainService;
import com.mycorp.finance.customer.domain.event.CustomerEventPublisher;
import com.mycorp.finance.global.security.domain.model.AuthUser;
import com.mycorp.finance.global.security.domain.repository.AuthRepository;
import com.mycorp.finance.global.security.infrastructure.config.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Command-side service responsible for handling write operations
 * related to Customer lifecycle such as registration, update, delete.
 *
 * Follows DDD principles, coordinating between domain logic and persistence.
 */
@Service
public class CustomerCommandServiceImpl implements CustomerCommandService {

    private final CustomerCommandRepository customerCommandRepository;
    private final CustomerDomainService customerDomainService;
    private final CustomerEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;

    public CustomerCommandServiceImpl(
            CustomerCommandRepository customerCommandRepository,
            CustomerDomainService customerDomainService,
            CustomerEventPublisher eventPublisher,
            PasswordEncoder passwordEncoder,
            AuthRepository authRepository
    ) {
        this.customerCommandRepository = customerCommandRepository;
        this.customerDomainService = customerDomainService;
        this.eventPublisher = eventPublisher;
        this.passwordEncoder = passwordEncoder;
        this.authRepository = authRepository;
    }

    /**
     * Registers a new customer. Handles both domain customer creation
     * and corresponding AuthUser persistence in a separate transaction.
     *
     * @param command Registration input DTO
     */
    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void registerCustomer(CustomerRegisterCommand command) {
        Password encodedPassword = Password.encode(command.password(), passwordEncoder);

        Customer customer = Customer.register(
                command.name(),
                command.email(),
                command.address(),
                command.phoneNumber(),
                encodedPassword,
                command.birthDate()
        );

        customerDomainService.validateCustomer(customer);
        customerCommandRepository.save(customer);

        // Persist auth credentials in separate auth DB transaction
        saveAuthUserTransactional(customer.getId(), command.email(), encodedPassword);

        eventPublisher.publishCustomerCreated(customer);
    }

    /**
     * Persists the corresponding authentication user to auth DB using
     * a dedicated transaction manager.
     *
     * @param customerId ID of the newly registered customer
     * @param email customer email
     * @param encodedPassword securely hashed password
     */
    @Transactional(transactionManager = "authTransactionManager")
    protected void saveAuthUserTransactional(UUID customerId, Email email, Password encodedPassword) {
        AuthUser authUser = AuthUser.register(customerId, email, encodedPassword);
        authRepository.save(authUser);
    }

    /**
     * Updates an existing customer's profile (name, address, phone).
     *
     * @param customerId ID of the customer to update
     * @param command DTO containing new customer values
     */
    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void updateCustomer(UUID customerId, CustomerUpdateCommand command) {
        Customer existingCustomer = customerCommandRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));

        Customer updatedCustomer = Customer.update(existingCustomer,
                command.name(),
                command.address(),
                command.phoneNumber());

        customerCommandRepository.save(updatedCustomer);

        eventPublisher.publishCustomerUpdated(updatedCustomer);
    }

    /**
     * Deletes a customer and corresponding auth user entity.
     *
     * @param customerId ID of the customer to delete
     */
    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void deleteCustomer(UUID customerId) {
        Customer existingCustomer = customerCommandRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));

        customerCommandRepository.delete(existingCustomer);

        // Also remove credentials from auth DB
        deleteAuthUserTransactional(customerId);

        eventPublisher.publishCustomerDeleted(existingCustomer);
    }

    /**
     * Removes the associated AuthUser from the auth DB.
     *
     * @param customerId ID to delete from auth repository
     */
    @Transactional(transactionManager = "authTransactionManager")
    protected void deleteAuthUserTransactional(UUID customerId) {
        authRepository.delete(customerId);
    }
}

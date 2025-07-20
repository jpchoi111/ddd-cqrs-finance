package com.mycorp.finance.customer.application.dto;

import com.mycorp.finance.customer.domain.model.vo.Address;
import com.mycorp.finance.customer.domain.model.vo.Email;
import com.mycorp.finance.customer.domain.model.vo.Name;
import com.mycorp.finance.customer.domain.model.vo.PhoneNumber;

import java.time.LocalDate;

/**
 * Command DTO for registering a new customer.
 * <p>
 * This DTO contains all necessary input data from external layer (Controller) to application layer.
 * </p>
 */
public record CustomerRegisterCommand(
        Name name,
        Email email,
        Address address,
        PhoneNumber phoneNumber,
        String password,  // raw password (to be validated and encrypted in application service)
        LocalDate birthDate
) { }

package com.mycorp.finance.customer.application.dto;

import com.mycorp.finance.customer.domain.model.vo.*;
import java.util.UUID;

public record CustomerUpdateCommand(
        UUID id,
        Name name,
        Address address,
        PhoneNumber phoneNumber
) {}

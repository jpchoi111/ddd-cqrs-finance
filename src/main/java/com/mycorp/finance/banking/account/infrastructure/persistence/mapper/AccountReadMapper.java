package com.mycorp.finance.banking.account.infrastructure.persistence.mapper;

import com.mycorp.finance.banking.account.application.dto.AccountReadDto;
import com.mycorp.finance.banking.account.domain.model.enums.AccountStatus;
import com.mycorp.finance.banking.account.infrastructure.persistence.entity.query.AccountReadEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert AccountReadEntity to AccountReadDto.
 */
@Component
public class AccountReadMapper {

    public AccountReadDto toDto(AccountReadEntity entity) {
        if (entity == null) {
            return null;
        }

        return AccountReadDto.from(entity);
    }

    /**
     * Converts Avro status string to domain AccountStatus enum.
     */
    public AccountStatus toAccountStatus(String avroStatus) {
        if (avroStatus == null || avroStatus.isBlank()) {
            return null;
        }

        try {
            return AccountStatus.valueOf(avroStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown status string: " + avroStatus, e);
        }
    }

}

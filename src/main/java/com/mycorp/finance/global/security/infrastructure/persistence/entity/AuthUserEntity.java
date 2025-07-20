package com.mycorp.finance.global.security.infrastructure.persistence.entity;

import com.mycorp.finance.customer.domain.model.vo.Email;
import com.mycorp.finance.customer.domain.model.vo.Password;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.mycorp.finance.global.security.domain.model.AuthUser;
import java.util.UUID;

/**
 * JPA Entity representing a user for authentication purposes.
 */
@Entity
@Table(name = "auth_users")
@Getter
@NoArgsConstructor
public class AuthUserEntity {

    @Id
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    public AuthUserEntity(UUID customerId, String email, String passwordHash) {
        this.customerId = customerId;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    /**
     * Converts JPA entity to domain model.
     *
     * @return AuthUser domain object
     */
    public AuthUser toDomain() {
        return AuthUser.fromExisting(
                customerId,
                new Email(email),
                Password.fromHashed(passwordHash)
        );
    }

    /**
     * Converts domain model to JPA entity.
     *
     * @param authUser domain AuthUser object
     * @return AuthUserEntity for persistence
     */
    public static AuthUserEntity fromDomain(AuthUser authUser) {
        return new AuthUserEntity(
                authUser.getCustomerId(),
                authUser.getEmail().value(),
                authUser.getPassword().value()
        );
    }
}

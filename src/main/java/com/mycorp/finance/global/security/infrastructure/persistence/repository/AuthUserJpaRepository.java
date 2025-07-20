package com.mycorp.finance.global.security.infrastructure.persistence.repository;

import com.mycorp.finance.global.security.infrastructure.persistence.entity.AuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for AuthUserEntity.
 */
public interface AuthUserJpaRepository extends JpaRepository<AuthUserEntity, UUID> {
    Optional<AuthUserEntity> findByEmail(String email);
}

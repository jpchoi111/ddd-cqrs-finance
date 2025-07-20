package com.mycorp.finance.global.security.infrastructure.persistence;

import com.mycorp.finance.global.security.domain.model.AuthUser;
import com.mycorp.finance.global.security.domain.repository.AuthRepository;
import com.mycorp.finance.global.security.infrastructure.persistence.entity.AuthUserEntity;
import com.mycorp.finance.global.security.infrastructure.persistence.repository.AuthUserJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of AuthRepository that interacts with the database using JPA.
 * Handles conversion between domain model and persistence entity.
 */
@Repository
@Transactional("authTransactionManager")
public class AuthRepositoryImpl implements AuthRepository {

    private final AuthUserJpaRepository jpaRepository;

    public AuthRepositoryImpl(AuthUserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<AuthUser> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(AuthUserEntity::toDomain);
    }

    @Override
    public void save(AuthUser authUser) {
        AuthUserEntity entity = AuthUserEntity.fromDomain(authUser);
        jpaRepository.save(entity);
    }

    @Override
    public void delete(UUID customerId) {
        jpaRepository.deleteById(customerId);
    }
}

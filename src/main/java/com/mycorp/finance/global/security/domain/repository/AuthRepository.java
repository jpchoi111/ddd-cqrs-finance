package com.mycorp.finance.global.security.domain.repository;

import com.mycorp.finance.global.security.domain.model.AuthUser;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing authentication user data.
 * Defines contract for persistence operations on AuthUser entities.
 */
public interface AuthRepository {

    /**
     * Finds an AuthUser by email.
     *
     * @param email the unique email identifier
     * @return an Optional containing the AuthUser if found, or empty otherwise
     */
    Optional<AuthUser> findByEmail(String email);

    /**
     * Saves a new or existing AuthUser.
     *
     * @param authUser the authentication user domain object to persist
     */
    void save(AuthUser authUser);


    void delete(UUID customerId);
}

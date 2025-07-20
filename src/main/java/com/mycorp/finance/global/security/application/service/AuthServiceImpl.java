package com.mycorp.finance.global.security.application.service;

import com.mycorp.finance.customer.domain.model.vo.Email;
import com.mycorp.finance.global.exception.domain.CustomerNotFoundException;
import com.mycorp.finance.global.security.domain.model.AuthUser;
import com.mycorp.finance.global.security.domain.repository.AuthRepository;
import com.mycorp.finance.global.security.infrastructure.config.PasswordEncoder;
import com.mycorp.finance.global.security.infrastructure.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, transactionManager = "authTransactionManager")
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(
            AuthRepository authRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Performs login by validating credentials and issuing a JWT token.
     *
     * @param email user's email VO
     * @param rawPassword raw (unhashed) password input
     * @return JWT token if authentication succeeds
     */
    @Override
    public String login(Email email, String rawPassword) {
        AuthUser authUser = authRepository.findByEmail(email.value())
                .orElseThrow(() -> new CustomerNotFoundException("User not found: " + email.value()));

        authUser.validatePassword(rawPassword, passwordEncoder);

        return jwtTokenProvider.createToken(authUser.getCustomerId());
    }
}

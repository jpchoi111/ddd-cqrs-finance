package com.mycorp.finance.global.security.presentation;

import com.mycorp.finance.customer.domain.model.vo.Email;
import com.mycorp.finance.global.security.application.service.AuthService;
import com.mycorp.finance.global.security.application.dto.LoginRequest;
import com.mycorp.finance.global.security.application.dto.TokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for handling authentication-related HTTP requests.
 * Exposes endpoints for login and potentially other auth functions like refresh tokens or logout.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles login requests.
     *
     * @param request Login credentials (email, password)
     * @return JWT token if authentication succeeds
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.login(new Email(request.email()), request.password());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}

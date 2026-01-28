package com.careconnect.controller;

import com.careconnect.dto.*;
import com.careconnect.repository.UserRepository;
import com.careconnect.service.impl.CustomUserDetailsService;
import com.careconnect.service.services.AuthService;
import com.careconnect.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(
            @Valid @RequestBody SignupRequest signupRequest,
            HttpServletRequest request
    ) {

        long startTime = System.currentTimeMillis();
        log.info("Signup request received for email: {}", signupRequest.getEmail());

        SignUpResponse response = authService.signUp(signupRequest);

        long duration = System.currentTimeMillis() - startTime;
        log.info("Signup successful for email: {} | timeTaken={}ms",
                signupRequest.getEmail(), duration);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletRequest request
    ) {

        long startTime = System.currentTimeMillis();
        log.info("login request received for email: {}", loginRequest.getEmail());

        LoginResponse response = authService.login(loginRequest);

        long duration = System.currentTimeMillis() - startTime;
        log.info("Login successful for email: {} | timeTaken={}ms",
                loginRequest.getEmail(), duration);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }
}



package com.careconnect.service.impl;

import com.careconnect.dto.LoginRequest;
import com.careconnect.dto.LoginResponse;
import com.careconnect.dto.SignUpResponse;
import com.careconnect.dto.SignupRequest;
import com.careconnect.exception.*;
import com.careconnect.repository.UserRepository;
import com.careconnect.service.services.AuthService;
import com.careconnect.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.careconnect.model.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final  JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Override
    public SignUpResponse signUp(SignupRequest signupRequest) {

        log.debug("Signup started for email={}", signupRequest.getEmail());

        // 1️⃣ Check if email already exists
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            log.warn("Signup failed - email already registered: {}",
                    signupRequest.getEmail());

            throw new DuplicateResourceException(
                    "Email is already registered. Please sign in."
            );
        }

        // 2️⃣ Create User entity
        User user = User.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        // 3️⃣ Save user
        User savedUser = userRepository.save(user);

        log.info("User registered successfully with id={}", savedUser.getId());

        // 4️⃣ Build response
        return SignUpResponse.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .message("User registered successfully")
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        log.debug("Login attempt for email={}", loginRequest.getEmail());

        // 1️⃣ Authenticate user (this does email + password check)
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            log.warn("Invalid login attempt for email={}", loginRequest.getEmail());
            throw new AuthenticationException("Invalid email or password");
        }

        // 2️⃣ Load user details
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(loginRequest.getEmail());

        // 3️⃣ Fetch domain user (for userId, email)
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        // 4️⃣ Generate JWT
        String token = jwtUtil.generateToken(userDetails);

        // 5️⃣ Build response
        return LoginResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getExpirationInSeconds())
                .build();
    }
}

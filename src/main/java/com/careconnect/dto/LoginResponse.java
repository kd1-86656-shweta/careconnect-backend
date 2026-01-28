package com.careconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long userId;
    private String email;
    private String accessToken;
    private String tokenType;   // "Bearer"
    private long expiresIn;     // seconds
}


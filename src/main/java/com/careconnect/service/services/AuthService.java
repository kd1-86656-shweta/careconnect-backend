package com.careconnect.service.services;

import com.careconnect.dto.LoginRequest;
import com.careconnect.dto.LoginResponse;
import com.careconnect.dto.SignUpResponse;
import com.careconnect.dto.SignupRequest;

public interface AuthService {

    public SignUpResponse signUp(SignupRequest signupRequest);

    public LoginResponse login(LoginRequest loginRequest);
}

package com.training.controllers;

import com.training.security.request.LoginRequest;
import com.training.security.request.SignupRequest;
import com.training.security.response.LoginResponse;
import com.training.security.response.MessageResponse;
import com.training.security.response.UserInfoResponse;
import com.training.services.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/public/signin")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/public/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        MessageResponse response = authService.registerUser(signUpRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfoResponse> getUserDetails(@RequestHeader("username") String username) {
        UserInfoResponse response = authService.getUserDetails(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username")
    public ResponseEntity<String> currentUserName(@RequestHeader("username") String username) {
        String response = authService.currentUserName(username);
        return ResponseEntity.ok(response);
    }
}

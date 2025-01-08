package com.chatapp.api.controller;

import com.chatapp.api.dto.AuthRequest;
import com.chatapp.api.dto.AuthResponse;
import com.chatapp.api.entity.User;
import com.chatapp.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        AuthResponse loginResponse = authService.login(authRequest.getEmail(), authRequest.getPassword());

        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User createdUser = authService.registerUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Could not register user");
        }
    }
}

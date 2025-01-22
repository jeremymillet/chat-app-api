package com.chatapp.api.controller;

import com.chatapp.api.dto.AuthRegisterRequest;
import com.chatapp.api.dto.AuthRequest;
import com.chatapp.api.dto.AuthResponse;
import com.chatapp.api.dto.UserDTO;

import com.chatapp.api.service.AuthService;
import com.chatapp.api.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        
        AuthResponse loginResponse = authService.login(authRequest.getEmail(), authRequest.getPassword());
        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRegisterRequest user) {
        try {
            UserDTO createdUser = authService.registerUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            if (userService.findUserByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with email " + user.getEmail() + " already exists.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not register user due to an unexpected error.");
        }
    }
}

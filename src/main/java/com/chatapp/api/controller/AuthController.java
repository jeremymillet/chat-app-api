package com.chatapp.api.controller;

import com.chatapp.api.dto.AuthRegisterRequest;
import com.chatapp.api.dto.AuthRequest;
import com.chatapp.api.dto.AuthResponse;
import com.chatapp.api.dto.UserDTO;

import com.chatapp.api.service.AuthService;
import com.chatapp.api.service.UserService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        AuthResponse authResponse = authService.refreshAccessToken(refreshToken);

        if (authResponse != null) {

            // Retourner la réponse avec le nouvel access token
            return ResponseEntity.ok(Map.of(
                    "accessToken", authResponse.getAccessToken(),
                    "refreshToken", authResponse.getRefreshToken()));
        }
        return ResponseEntity.status(401).body("refresh token a expirée");
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

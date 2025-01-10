package com.chatapp.api.service;

import com.chatapp.api.util.JwtUtil;
import com.chatapp.api.dto.AuthRegisterRequest;
import com.chatapp.api.dto.AuthResponse;
import com.chatapp.api.dto.UserDTO;
import com.chatapp.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponse login(String email, String password) {
        Optional<User> userOptional = userService.findUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtUtil.generateToken(user.getEmail());
                return new AuthResponse(token, userService.getUserById(user.getId()));
            }
        }
        return null;
    }

    public UserDTO registerUser(AuthRegisterRequest authRequest) {
        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        return userService.createUser(user);
    }
}

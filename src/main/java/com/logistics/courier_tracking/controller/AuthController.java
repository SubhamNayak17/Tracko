package com.logistics.courier_tracking.controller;

import com.logistics.courier_tracking.dto.ApiResponse;
import com.logistics.courier_tracking.dto.LoginRequest;
import com.logistics.courier_tracking.dto.RegisterRequest;
import com.logistics.courier_tracking.entity.User;
import com.logistics.courier_tracking.exception.BadRequestException;
import com.logistics.courier_tracking.exception.DuplicateResourceException;
import com.logistics.courier_tracking.repository.UserRepository;
import com.logistics.courier_tracking.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User already exists with email: " + request.getEmail());
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Set role
        if (request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN")) {
            user.setRole(User.Role.ROLE_ADMIN);
        } else {
            user.setRole(User.Role.ROLE_USER);
        }

        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.created("User registered successfully", "Registration successful!"));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(ApiResponse.success("Login successful", token));
    }
}
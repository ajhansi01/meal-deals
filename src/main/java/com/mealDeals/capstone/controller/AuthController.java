package com.mealDeals.capstone.controller;

import com.mealDeals.capstone.model.User;
import com.mealDeals.capstone.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Authentication")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public static class RegisterRequest {
        private String name;
        private String email;
        private String password;
        private String role;

        // Getters and setters
    }

    public static class LoginRequest {
        private String email;
        private String password;

        // Getters and setters
    }

    public static class JwtResponse {
        private String token;

        public JwtResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
    @ApiOperation(value = "Register a new user", notes = "Create a new user with email, name, and password.")
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        try {
            Role role = Role.valueOf(registerRequest.getRole().toUpperCase());
            User user = userService.registerUser(registerRequest.getName(), registerRequest.getEmail(), registerRequest.getPassword(), role);
            return "User registered successfully!";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }
    @ApiOperation(value = "Login a user", notes = "Authenticate a user and return a JWT token.")
    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            String jwtToken = jwtUtil.generateToken(loginRequest.getEmail());
            return new JwtResponse(jwtToken);
        } catch (Exception e) {
            return new JwtResponse("Invalid email or password");
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}

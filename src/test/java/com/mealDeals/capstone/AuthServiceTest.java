package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.User;
import com.mealDeals.capstone.repository.UserRepository;
import com.mealDeals.capstone.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
    }

    @Test
    public void authenticateUser_shouldReturnToken() {
        when(userRepository.findByUsername("testUser")).thenReturn(java.util.Optional.of(user));
        when(jwtTokenProvider.generateToken(user)).thenReturn("mockJwtToken");

        String token = authService.authenticateUser("testUser", "password");

        assertNotNull(token);
        assertEquals("mockJwtToken", token);
    }

    @Test
    public void authenticateUser_invalidCredentials_shouldThrowException() {
        when(userRepository.findByUsername("testUser")).thenReturn(java.util.Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> {
            authService.authenticateUser("testUser", "wrongPassword");
        });
    }
}


package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.Payment;
import com.mealDeals.capstone.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = new Payment();
        payment.setAmount(100.0);
        payment.setStatus("Pending");
    }

    @Test
    public void processPayment_shouldReturnPayment() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment processedPayment = paymentService.processPayment(payment);

        assertNotNull(processedPayment);
        assertEquals("Pending", processedPayment.getStatus());
    }
}
package com.mealDeals.capstone.controller;

import com.mealDeals.capstone.model.User;
import com.mealDeals.capstone.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
        import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void authenticateUser_shouldReturnJwtToken() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        when(authService.authenticateUser("testUser", "password")).thenReturn("mockJwtToken");

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockJwtToken"));
    }
}

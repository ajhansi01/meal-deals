package com.mealDeals.capstone.controller;


import com.mealDeals.capstone.model.Payment;
import com.mealDeals.capstone.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Payment Processing")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Process payment for an order
    @ApiOperation(value = "Process payment", notes = "Simulate payment processing for the order.")
    @PostMapping("/process")
    public Payment processPayment(@RequestBody Order order) {
        // Ensure the order exists
        if (order == null || order.getId() == null) {
            throw new IllegalArgumentException("Order ID is required to process payment");
        }

        // Simulate the payment process and return the payment status
        return paymentService.processPayment(order);
    }
}

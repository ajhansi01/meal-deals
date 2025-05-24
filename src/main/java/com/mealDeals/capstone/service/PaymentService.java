package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.Order;
import com.mealDeals.capstone.model.Payment;
import com.mealDeals.capstone.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    // Simulate the payment process
    public Payment processPayment(Order order) {
        // Simulate a random payment outcome (either success or failure)
        Random random = new Random();
        boolean paymentSuccess = random.nextBoolean();  // Randomly decides payment outcome

        // Create a new payment entry
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(paymentSuccess ? "SUCCESS" : "FAILED");

        // Save payment to the database
        payment = paymentRepository.save(payment);

        // If payment is successful, update order status to "COMPLETED"
        if (paymentSuccess) {
            order.setStatus(OrderStatus.COMPLETED);
            orderService.updateOrder(order);
        }

        return payment;
    }
}

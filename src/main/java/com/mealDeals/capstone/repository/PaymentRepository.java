package com.mealDeals.capstone.repository;

import com.mealDeals.capstone.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

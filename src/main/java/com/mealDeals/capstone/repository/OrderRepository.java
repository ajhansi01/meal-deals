package com.mealDeals.capstone.repository;

import com.mealDeals.capstone.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);  // Custom query to get orders by user
}

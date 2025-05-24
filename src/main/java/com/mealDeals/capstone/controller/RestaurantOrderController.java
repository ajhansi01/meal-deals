package com.mealDeals.capstone.controller;

import com.mealDeals.capstone.model.Order;
import com.mealDeals.capstone.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantOrderController {

    @Autowired
    private OrderService orderService;

    // Get all orders for a restaurant
    @GetMapping("/{restaurantId}/orders")
    public List<Order> getRestaurantOrders(@PathVariable Long restaurantId) {
        // Logic to return orders for a specific restaurant (you can add filtering for pending orders)
        return orderService.getRestaurantOrders(restaurantId);
    }
}

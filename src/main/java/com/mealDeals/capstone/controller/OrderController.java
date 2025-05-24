package com.mealDeals.capstone.controller;

import com.mealDeals.capstone.model.Order;
import com.mealDeals.capstone.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Order Management")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Checkout (place an order)
    @ApiOperation(value = "Place an order", notes = "Customers can place orders by selecting menu items.")
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/checkout")
    public Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    // View order status
    @ApiOperation(value = "View order details", notes = "Retrieve details of a specific order.")
    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    // Cancel an order
    @ApiOperation(value = "Cancel an order", notes = "Customers can cancel orders within a certain timeframe.")
    @PutMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }

    // Get all past orders for the authenticated user
    @GetMapping
    public List<Order> getAllOrdersForUser(@RequestParam Long userId) {
        return orderService.getAllOrdersForUser(userId);  // Get orders by user ID
    }

    // Get details of a specific order
    @GetMapping("/{orderId}")
    public Optional<Order> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    // Get the status of a specific order
    @GetMapping("/{orderId}/status")
    public String getOrderStatus(@PathVariable Long orderId) {
        return orderService.getOrderStatus(orderId);
    }


}

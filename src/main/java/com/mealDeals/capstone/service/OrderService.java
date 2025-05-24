package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.*;
import com.mealDeals.capstone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private DiscountService discountService;

    // Fetch all past orders for a user
    public List<Order> getAllOrdersForUser(Long userId) {
        return orderRepository.findByUserId(userId);  // Custom query to get orders by user
    }

    // Fetch a specific order by its ID
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    // Fetch order status
    public String getOrderStatus(Long orderId) {
        Optional<Order> order = getOrderById(orderId);
        return order.map(Order::getStatus).orElse("Order not found");
    }

    // Place an order (checkout) with discount
    public Order placeOrder(Long userId, Long restaurantId, List<Long> menuItemIds, List<Integer> quantities, String discountCode) {
        // Validate and apply discount if provided
        Discount discount = null;
        if (discountCode != null && !discountCode.isEmpty()) {
            discount = discountService.validateDiscount(discountCode);
        }

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        // Verify if all items belong to the same restaurant
        for (Long menuItemId : menuItemIds) {
            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));
            if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
                throw new IllegalArgumentException("All items must belong to the same restaurant");
            }
        }

        // Create Order
        Order order = new Order();
        order.setUser(new User()); // Assign the current user
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderTime(LocalDateTime.now());
        order.setDiscountApplied(discount != null ? discount.getCode() : null);

// Calculate total amount
        double totalAmount = 0;
        for (int i = 0; i < menuItemIds.size(); i++) {
            MenuItem menuItem = menuItemRepository.findById(menuItemIds.get(i))
                    .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(quantities.get(i));
            orderItem.setPrice(menuItem.getPrice() * quantities.get(i));
            orderItem.setOrder(order);
            totalAmount += orderItem.getPrice();
            orderItemRepository.save(orderItem);
        }

        // Apply discount if available
        if (discount != null) {
            totalAmount = totalAmount - (totalAmount * discount.getValue() / 100);
            discountService.useDiscount(discount); // Decrease uses remaining for the discount
        }

        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);

        return order;
    }


    // Cancel an order within a certain time frame (e.g., 10 minutes)
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getStatus() == OrderStatus.PENDING && order.getOrderTime().plusMinutes(10).isAfter(LocalDateTime.now())) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setCancelledAt(LocalDateTime.now());
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order cannot be cancelled.");
        }
    }

    // Get order status
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    // Update the status of an order (e.g., from pending to completed)
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }
}

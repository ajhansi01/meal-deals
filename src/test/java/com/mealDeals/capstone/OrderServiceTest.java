package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.Order;
import com.mealDeals.capstone.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setStatus("Pending");
    }

    @Test
    public void placeOrder_shouldReturnOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order placedOrder = orderService.placeOrder(order);

        assertNotNull(placedOrder);
        assertEquals("Pending", placedOrder.getStatus());
    }

    @Test
    public void cancelOrder_shouldReturnCancelledOrder() {
        Long orderId = 1L;
        order.setStatus("Pending");
        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(order));

        Order cancelledOrder = orderService.cancelOrder(orderId);

        assertNotNull(cancelledOrder);
        assertEquals("Cancelled", cancelledOrder.getStatus());
    }
}

package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.Restaurant;
import com.mealDeals.capstone.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setLocation("Test Location");
    }

    @Test
    public void createRestaurant_shouldReturnRestaurant() {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);

        assertNotNull(createdRestaurant);
        assertEquals("Test Restaurant", createdRestaurant.getName());
    }

    @Test
    public void getRestaurant_shouldReturnRestaurant() {
        Long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(java.util.Optional.of(restaurant));

        Restaurant foundRestaurant = restaurantService.getRestaurant(restaurantId);

        assertNotNull(foundRestaurant);
        assertEquals("Test Restaurant", foundRestaurant.getName());
    }
}

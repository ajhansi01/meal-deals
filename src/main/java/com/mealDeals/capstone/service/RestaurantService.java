package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.Restaurant;
import com.mealDeals.capstone.model.User;
import com.mealDeals.capstone.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mealDeals.capstone.exception.BadRequestException;
import com.mealDeals.capstone.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserService userService;


    // Create a new restaurant (only accessible to admin or restaurant owner)
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT_OWNER')")
    @PostMapping
    public Restaurant createRestaurant(Restaurant restaurant) {
        if (restaurantRepository.existsByRestaurantName(restaurant.getRestaurantName())) {
            throw new BadRequestException("Restaurant name already exists.");
        }
        return restaurantRepository.save(restaurant);
    }

    // Get all restaurants (for Admin and Restaurant Owners)
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    // Update restaurant details (only accessible to restaurant owners)
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PutMapping("/{restaurantId}")
    public Restaurant updateRestaurant(Long restaurantId, Restaurant restaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found."));
        existingRestaurant.setName(restaurant.getName());
        existingRestaurant.setAddress(restaurant.getAddress());
        return restaurantRepository.save(existingRestaurant);
    }


    // View a specific restaurant (accessible by anyone)
    @GetMapping("/{restaurantId}")
    public Restaurant getRestaurant(@PathVariable Long restaurantId) {
        return restaurantService.getRestaurant(restaurantId);
    }


    // Delete restaurant
    public void deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        restaurantRepository.delete(restaurant);
    }
}

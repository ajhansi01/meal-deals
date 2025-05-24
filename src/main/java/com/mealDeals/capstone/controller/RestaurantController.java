package com.mealDeals.capstone.controller;

import com.mealDeals.capstone.model.Restaurant;
import com.mealDeals.capstone.service.RestaurantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Restaurant Management")
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // Create a new restaurant (only for Restaurant Owners and Admin)
    @ApiOperation(value = "Create a new restaurant", notes = "Allows admins and restaurant owners to create a new restaurant.")
    @PostMapping
    public Restaurant createRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        return restaurantService.createRestaurant(
                restaurantRequest.getName(),
                restaurantRequest.getAddress(),
                restaurantRequest.getContactNumber(),
                restaurantRequest.getOwnerId()
        );
    }

    // Get all restaurants (for Admin and Restaurant Owners)
    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    // View a single restaurant by its ID
    @ApiOperation(value = "Get restaurant details", notes = "Get details of a specific restaurant.")
    @GetMapping("/{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable Long restaurantId) {
        return restaurantService.getRestaurantById(restaurantId);
    }

    // Update an existing restaurant (only for Restaurant Owners and Admin)
    @ApiOperation(value = "Update restaurant details", notes = "Allows restaurant owners to update their restaurant details.")
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    @PutMapping("/{restaurantId}")
    public Restaurant updateRestaurant(
            @PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantRequest restaurantRequest
    ) {
        return restaurantService.updateRestaurant(
                restaurantId,
                restaurantRequest.getName(),
                restaurantRequest.getAddress(),
                restaurantRequest.getContactNumber()
        );
    }

    @ApiOperation(value = "Get restaurant details", notes = "Get details of a specific restaurant.")
    @GetMapping("/{restaurantId}")
    public Restaurant getRestaurant(
            @ApiParam(value = "Restaurant ID", required = true)
            @PathVariable Long restaurantId) {
        return restaurantService.getRestaurant(restaurantId);
    }

    // Delete a restaurant
    @ApiOperation(value = "Delete restaurant", notes = "Delete a restaurant by its ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{restaurantId}")
    public void deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
    }

    // Request Body to Create or Update a Restaurant
    public static class RestaurantRequest {
        private String name;
        private String address;
        private String contactNumber;
        private Long ownerId;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public Long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
        }
    }
}

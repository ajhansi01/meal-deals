package com.mealDeals.capstone.controller;


import com.mealDeals.capstone.model.MenuItem;
import com.mealDeals.capstone.service.MenuItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Menu Item Management")
@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menu")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    // Add a new menu item to a restaurant
    @ApiOperation(value = "Add a new menu item", notes = "Add a menu item to the restaurant's menu.")
    @PostMapping
    public MenuItem addMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody MenuItemRequest menuItemRequest
    ) {
        return menuItemService.addMenuItem(
                restaurantId,
                menuItemRequest.getName(),
                menuItemRequest.getDescription(),
                menuItemRequest.getPrice()
        );
    }

    // Update an existing menu item
    @ApiOperation(value = "Update a menu item", notes = "Update an existing menu item.")
    @PutMapping("/{menuItemId}")
    public MenuItem updateMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId,
            @RequestBody MenuItemRequest menuItemRequest
    ) {
        return menuItemService.updateMenuItem(
                restaurantId,
                menuItemId,
                menuItemRequest.getName(),
                menuItemRequest.getDescription(),
                menuItemRequest.getPrice()
        );
    }

    // Delete a menu item
    @ApiOperation(value = "Delete a menu item", notes = "Delete a menu item from the restaurant's menu.")
    @DeleteMapping("/{menuItemId}")
    public void deleteMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long menuItemId
    ) {
        menuItemService.deleteMenuItem(restaurantId, menuItemId);
    }

    // Get all menu items for a restaurant
    @GetMapping
    public List<MenuItem> getMenuItems(@PathVariable Long restaurantId) {
        return menuItemService.getMenuItems(restaurantId);
    }

    // Request Body for Menu Item
    public static class MenuItemRequest {
        private String name;
        private String description;
        private double price;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}

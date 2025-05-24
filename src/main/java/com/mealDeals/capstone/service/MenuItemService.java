package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.MenuItem;
import com.mealDeals.capstone.model.Restaurant;
import com.mealDeals.capstone.repository.MenuItemRepository;
import com.mealDeals.capstone.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Add new menu item to a restaurant
    public MenuItem addMenuItem(Long restaurantId, String name, String description, double price) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setDescription(description);
        menuItem.setPrice(price);
        menuItem.setRestaurant(restaurant);

        return menuItemRepository.save(menuItem);
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        if (menuItem.getPrice() < 0) {
            throw new BadRequestException("Price must be non-negative.");
        }
        return menuItemRepository.save(menuItem);
    }


    // Update existing menu item
    public MenuItem updateMenuItem(Long restaurantId, Long menuItemId, String name, String description, double price) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

        menuItem.setName(name);
        menuItem.setDescription(description);
        menuItem.setPrice(price);
        return menuItemRepository.save(menuItem);
    }

    // Delete menu item
    public void deleteMenuItem(Long restaurantId, Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));
        menuItemRepository.delete(menuItem);
    }

    // Get all menu items for a restaurant
    public List<MenuItem> getMenuItems(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }
}

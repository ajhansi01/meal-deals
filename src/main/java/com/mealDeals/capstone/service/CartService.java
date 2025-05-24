package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.Cart;
import com.mealDeals.capstone.model.CartItem;
import com.mealDeals.capstone.model.MenuItem;
import com.mealDeals.capstone.model.User;
import com.mealDeals.capstone.repository.CartRepository;
import com.mealDeals.capstone.repository.CartItemRepository;
import com.mealDeals.capstone.repository.MenuItemRepository;
import com.mealDeals.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    // Get or create a cart for a user
    public Cart getOrCreateCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setRestaurantId(null); // Initially, no restaurant selected
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    // Add an item to the cart
    public CartItem addItemToCart(Long userId, Long menuItemId, int quantity) {
        Cart cart = getOrCreateCart(userId);
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

        // Check if the item is from the same restaurant
        if (cart.getRestaurantId() == null) {
            cart.setRestaurantId(menuItem.getRestaurant().getId());
        } else if (!cart.getRestaurantId().equals(menuItem.getRestaurant().getId())) {
            throw new IllegalArgumentException("Cannot add items from different restaurants to the same cart.");
        }

        // Check if the item already exists in the cart
        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndMenuItem(cart, menuItem);
        if (existingCartItem.isPresent()) {
            // If the item exists, just update the quantity
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return cartItemRepository.save(cartItem);
        }

        // Otherwise, create a new CartItem
        CartItem newCartItem = new CartItem();
        newCartItem.setCart(cart);
        newCartItem.setMenuItem(menuItem);
        newCartItem.setQuantity(quantity);

        return cartItemRepository.save(newCartItem);
    }

    // Update cart item quantity
    public CartItem updateCartItem(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    // Remove an item from the cart
    public void removeCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        cartItemRepository.delete(cartItem);
    }
}

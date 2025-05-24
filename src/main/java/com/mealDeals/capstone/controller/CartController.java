package com.mealDeals.capstone.controller;

import com.mealDeals.capstone.model.CartItem;
import com.mealDeals.capstone.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add an item to the cart
    @PostMapping
    public CartItem addItemToCart(
            @RequestParam Long userId,
            @RequestParam Long menuItemId,
            @RequestParam int quantity
    ) {
        return cartService.addItemToCart(userId, menuItemId, quantity);
    }

    // Update the quantity of a cart item
    @PutMapping("/{cartItemId}")
    public CartItem updateCartItem(
            @PathVariable Long cartItemId,
            @RequestParam int quantity
    ) {
        return cartService.updateCartItem(cartItemId, quantity);
    }

    // Remove an item from the cart
    @DeleteMapping("/{cartItemId}")
    public void removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
    }
}

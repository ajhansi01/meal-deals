package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.Discount;
import com.mealDeals.capstone.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    // Create a new discount code
    public Discount createDiscount(Discount discount) {
        discount.setActive(true);
        discount.setUsesRemaining(discount.getMaxUses()); // Initialize uses remaining
        return discountRepository.save(discount);
    }

    // Get available discounts
    public Iterable<Discount> getAvailableDiscounts() {
        return discountRepository.findAll();
    }

    // Validate if discount code is applicable
    public Discount validateDiscount(String code) {
        Optional<Discount> discountOpt = discountRepository.findByCode(code);
        if (discountOpt.isPresent()) {
            Discount discount = discountOpt.get();
            // Check if the discount is active
            if (!discount.isActive()) {
                throw new IllegalArgumentException("This discount code is no longer active.");
            }
            // Check if the discount has expired
            if (discount.getExpirationDate().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("This discount code has expired.");
            }
            // Check if there are uses remaining
            if (discount.getUsesRemaining() <= 0) {
                throw new IllegalArgumentException("This discount code has no uses remaining.");
            }
            return discount;
        } else {
            throw new IllegalArgumentException("Invalid discount code.");
        }
    }

    // Decrease the usage of the discount code after it is applied
    public void useDiscount(Discount discount) {
        discount.setUsesRemaining(discount.getUsesRemaining() - 1);
        discountRepository.save(discount);
    }
}

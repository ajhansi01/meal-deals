package com.mealDeals.capstone.controller;

import com.mealDeals.capstone.model.Discount;
import com.mealDeals.capstone.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    // Create a new discount code
    @PostMapping
    public Discount createDiscount(@RequestBody Discount discount) {
        return discountService.createDiscount(discount);
    }

    // Get all available discounts
    @GetMapping
    public Iterable<Discount> getAvailableDiscounts() {
        return discountService.getAvailableDiscounts();
    }
}

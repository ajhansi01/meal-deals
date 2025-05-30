package com.mealDeals.capstone.repository;

import com.mealDeals.capstone.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}

package com.pcm.gestionRestaurant.services;

import com.pcm.gestionRestaurant.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository  extends JpaRepository<Restaurant, Integer> {
}

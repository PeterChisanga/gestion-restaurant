package com.pcm.gestionRestaurant.services;

import com.pcm.gestionRestaurant.models.Plat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatRepository extends JpaRepository<Plat, Integer> {
    List<Plat> findByRestaurantId(int restaurantId);
}

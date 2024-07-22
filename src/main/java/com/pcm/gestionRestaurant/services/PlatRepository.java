package com.pcm.gestionRestaurant.services;

import com.pcm.gestionRestaurant.models.Plat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatRepository extends JpaRepository<Plat, Integer> {
}

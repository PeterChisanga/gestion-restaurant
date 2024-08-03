package com.pcm.gestionRestaurant.services;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pcm.gestionRestaurant.models.Employe;

import java.util.List;

public interface EmployeRepository extends JpaRepository<Employe, Integer> {

    Employe findByEmail(String email);

    List<Employe> findByRestaurantId(int restaurantId);
}

package com.pcm.gestionRestaurant.services;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pcm.gestionRestaurant.models.Employe;

public interface EmployeRepository extends JpaRepository<Employe, Integer> {

}

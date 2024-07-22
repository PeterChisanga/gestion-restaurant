package com.pcm.gestionRestaurant.services;

import com.pcm.gestionRestaurant.models.Plat;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pcm.gestionRestaurant.models.Commande;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    List<Commande> findByPlat(Plat plat);
}

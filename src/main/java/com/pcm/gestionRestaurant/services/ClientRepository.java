package com.pcm.gestionRestaurant.services;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pcm.gestionRestaurant.models.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {}

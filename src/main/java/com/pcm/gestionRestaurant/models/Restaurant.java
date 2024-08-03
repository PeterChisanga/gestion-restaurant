package com.pcm.gestionRestaurant.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nom;

    @Column
    private String address;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Date createdAt;

    public Restaurant() {
        this.createdAt = new Date();
    }

    public Restaurant(String nom, String address) {
        this.nom = nom;
        this.address = address;
        this.createdAt = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}

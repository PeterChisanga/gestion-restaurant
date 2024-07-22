package com.pcm.gestionRestaurant.models;

import jakarta.persistence.Column;

import java.util.Date;

public class EmployeDto {
    private String nom;

    private String email;

    private String password;

    public EmployeDto() {

    }

    public EmployeDto( String nom, String email, String password) {
        this.nom = nom;
        this.email = email;
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

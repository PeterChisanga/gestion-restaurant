package com.pcm.gestionRestaurant.models;

public class RestaurantDto {
    private int id;
    private String nom;
    private String address;

    private String nomEmploye;

    public String getNomEmploye() {
        return this.nomEmploye;
    }

    public void setNomEmploye(String nomEmploye) {
        this.nomEmploye = nomEmploye;
    }

    public String getEmailEmploye() {
        return emailEmploye;
    }

    public void setEmailEmploye(String emailEmploye) {
        this.emailEmploye = emailEmploye;
    }

    public String getPasswordEmploye() {
        return passwordEmploye;
    }

    public void setPasswordEmploye(String passwordEmploye) {
        this.passwordEmploye = passwordEmploye;
    }

    private String emailEmploye;
    private String passwordEmploye;

    public RestaurantDto() {
    }

    public RestaurantDto(int id, String nom, String address) {
        this.id = id;
        this.nom = nom;
        this.address = address;
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
}

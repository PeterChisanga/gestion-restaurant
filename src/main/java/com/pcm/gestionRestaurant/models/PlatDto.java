package com.pcm.gestionRestaurant.models;

public class PlatDto {
    private String nom;

    private Double prix;

    private String description;

    public PlatDto() {

    }

    public PlatDto(String nom, Double prix, String description) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
}
package com.pcm.gestionRestaurant.models;

public class CommandeDto {
    private String nomClient;
    private int platId;

    public CommandeDto() {}

    public CommandeDto(String nomClient, int platId) {
        this.nomClient = nomClient;
        this.platId = platId;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public int getPlatId() {
        return platId;
    }

    public void setPlatId(int platId) {
        this.platId = platId;
    }
}

package com.example.appsanst.ressources;

public class Aliment {
    private String nom;
    private String quantite;
    private int calories;

    public Aliment(String nom, String quantite, int calories) {
        this.nom = nom;
        this.quantite = quantite;
        this.calories = calories;
    }

    public String getNom() {
        return nom;
    }

    public String getQuantite() {
        return quantite;
    }

    public int getCalories() {
        return calories;
    }
}

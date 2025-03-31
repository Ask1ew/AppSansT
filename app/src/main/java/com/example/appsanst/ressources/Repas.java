package com.example.appsanst.ressources;

public class Repas {
    private String nom;
    private int calories;
    private int glucides;
    private int proteines;
    private int lipides;

    public Repas(String nom, int calories, int glucides, int proteines, int lipides) {
        this.nom = nom;
        this.calories = calories;
        this.glucides = glucides;
        this.proteines = proteines;
        this.lipides = lipides;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public int getCalories() {
        return calories;
    }

    public int getGlucides() {
        return glucides;
    }

    public int getProteines() {
        return proteines;
    }

    public int getLipides() {
        return lipides;
    }
}

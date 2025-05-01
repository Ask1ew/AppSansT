package com.example.appsanst.ressources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Repas {
    private String nom;
    private int calories;
    private int glucides;
    private int proteines;
    private int lipides;
    private String date;

    public Repas(String nom, int calories, int glucides, int proteines, int lipides) {
        this.nom = nom;
        this.calories = calories;
        this.glucides = glucides;
        this.proteines = proteines;
        this.lipides = lipides;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        this.date = sdf.format(new Date());
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

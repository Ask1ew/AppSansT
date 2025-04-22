package com.example.appsanst.ressources;

public class Aliment {

    private final String nom;
    private final String quantite;

    private final int calories;
    private final int glucides;
    private final int proteines;
    private final int lipides;

    public Aliment(String nom, String quantite,
                   int calories, int glucides, int proteines, int lipides) {
        this.nom       = nom;
        this.quantite  = quantite;
        this.calories  = calories;
        this.glucides  = glucides;
        this.proteines = proteines;
        this.lipides   = lipides;
    }

    // getters
    public String getNom()        { return nom; }
    public String getQuantite()   { return quantite; }
    public int    getCalories()   { return calories; }
    public int    getGlucides()   { return glucides; }
    public int    getProteines()  { return proteines; }
    public int    getLipides()    { return lipides; }
}

package com.example.appsanst.ressources;

public class Exercice {
    private String nom;
    private int series;
    private int repetitions;
    private int tempsRepos; // en secondes

    public Exercice(String nom, int series, int repetitions, int tempsRepos) {
        this.nom = nom;
        this.series = series;
        this.repetitions = repetitions;
        this.tempsRepos = tempsRepos;
    }

    public String getNom() {
        return nom;
    }

    public int getSeries() {
        return series;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public int getTempsRepos() {
        return tempsRepos;
    }
}


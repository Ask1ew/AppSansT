// Exercice.java – Ajout d'un champ 'poids' et implémentation Serializable
package com.example.appsanst.ressources;

import java.io.Serializable;

public class Exercice implements Serializable {
    private String nom;
    private int series;
    private int repetitions;
    private int tempsRepos; // en secondes
    private int poids;      // poids en kg (0 si non renseigné)

    // Constructeur existant (poids non spécifié -> 0 par défaut)
    public Exercice(String nom, int series, int repetitions, int tempsRepos) {
        this(nom, series, repetitions, tempsRepos, 0);
    }

    // Nouveau constructeur incluant le poids
    public Exercice(String nom, int series, int repetitions, int tempsRepos, int poids) {
        this.nom = nom;
        this.series = series;
        this.repetitions = repetitions;
        this.tempsRepos = tempsRepos;
        this.poids = poids;
    }

    // Getters
    public String getNom()           { return nom; }
    public int getSeries()           { return series; }
    public int getRepetitions()      { return repetitions; }
    public int getTempsRepos()       { return tempsRepos; }
    public int getPoids()            { return poids; }
}

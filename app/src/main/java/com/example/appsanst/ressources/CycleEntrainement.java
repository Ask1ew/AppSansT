package com.example.appsanst.ressources;

import java.util.List;

public class CycleEntrainement {
    private String nom;
    private List<Exercice> exercices;

    public CycleEntrainement(String nom, List<Exercice> exercices) {
        this.nom = nom;
        this.exercices = exercices;
    }

    public String getNom() {
        return nom;
    }

    public List<Exercice> getExercices() {
        return exercices;
    }
}

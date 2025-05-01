// CycleEntrainement.java – Implémentation Serializable et méthodes utilitaires
package com.example.appsanst.ressources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CycleEntrainement implements Serializable {
    private String nom;
    private List<Exercice> exercices;

    public CycleEntrainement(String nom, List<Exercice> exercices) {
        this.nom = nom;
        this.exercices = exercices;
    }
    public String getNom()              { return nom; }
    public List<Exercice> getExercices(){ return exercices; }

    // Liste statique de tous les cycles (préconfigurés + ajoutés par l'utilisateur)
    public static List<CycleEntrainement> allCyclesList = new ArrayList<>(getPreconfiguredCycles());

    // Cycles d'entraînement préconfigurés
    public static List<CycleEntrainement> getPreconfiguredCycles() {
        List<CycleEntrainement> cycles = new ArrayList<>();
        cycles.add(new CycleEntrainement(
                "Full Body Débutant",
                Arrays.asList(
                        new Exercice("Pompes", 3, 10, 60),
                        new Exercice("Squats", 3, 15, 60),
                        new Exercice("Crunchs", 3, 20, 45)
                )
        ));
        cycles.add(new CycleEntrainement(
                "Haut du corps",
                Arrays.asList(
                        new Exercice("Pompes", 4, 12, 60),
                        new Exercice("Dips", 3, 8, 90),
                        new Exercice("Tractions", 3, 6, 90)
                )
        ));
        cycles.add(new CycleEntrainement(
                "Bas du corps",
                Arrays.asList(
                        new Exercice("Squats", 4, 15, 60),
                        new Exercice("Fentes", 3, 12, 60),
                        new Exercice("Mollets debout", 3, 20, 45)
                )
        ));
        cycles.add(new CycleEntrainement(
                "Cardio express",
                Arrays.asList(
                        new Exercice("Burpees", 3, 12, 60),
                        new Exercice("Montées de genoux", 3, 30, 45),
                        new Exercice("Jumping jacks", 3, 40, 45)
                )
        ));
        return cycles;
    }
}

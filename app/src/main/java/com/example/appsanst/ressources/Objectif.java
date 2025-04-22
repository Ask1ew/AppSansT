package com.example.appsanst.ressources;

public class Objectif {
    private String titre;
    private String description;
    private int cible; // Ex: 5 kg, 10 000 pas
    private int progres; // Progression actuelle

    public Objectif(String titre, String description, int cible, int progres) {
        this.titre = titre;
        this.description = description;
        this.cible = cible;
        this.progres = progres;
    }

    // Getters
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public int getCible() { return cible; }
    public int getProgres() { return progres; }
}

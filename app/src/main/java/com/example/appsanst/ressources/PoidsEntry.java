package com.example.appsanst.ressources;

import java.util.Date;

public class PoidsEntry {
    private final Date date;
    private final float poids; // en kg

    public PoidsEntry(Date date, float poids) {
        this.date = date;
        this.poids = poids;
    }

    public Date getDate() { return date; }
    public float getPoids() { return poids; }
}

package com.example.appsanst;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.ressources.Objectif;
import com.example.appsanst.ressources.ObjectifAdapter;
import java.util.Arrays;
import java.util.List;

public class ObjectifsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectifs);

        // Initialisation des objectifs
        List<Objectif> objectifs = Arrays.asList(
                new Objectif(
                        "Poids",
                        "Atteindre votre poids idéal",
                        70, // Cible
                        65  // Progression actuelle
                ),
                new Objectif(
                        "Pas quotidiens",
                        "Objectif de marche quotidienne",
                        10000,
                        7500
                ),
                new Objectif(
                        "Hydratation",
                        "Verres d'eau par jour",
                        8,
                        5
                )
        );

        RecyclerView recyclerView = findViewById(R.id.recyclerView_objectifs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ObjectifAdapter(objectifs));
    }
}

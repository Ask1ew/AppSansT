package com.example.appsanst;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsanst.ressources.CycleEntrainement;
import com.example.appsanst.ressources.Exercice;
import com.example.appsanst.ressources.CycleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntrainementActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrainement);

        // Préparer les cycles d'entraînement préconfigurés
        List<CycleEntrainement> cycles = getCyclesPreconfigures();

        RecyclerView recyclerView = findViewById(R.id.recyclerView_cycles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CycleAdapter(cycles));
    }

    private List<CycleEntrainement> getCyclesPreconfigures() {
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

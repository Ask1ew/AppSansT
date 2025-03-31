package com.example.appsanst;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.R;
import com.example.appsanst.ressources.Repas;
import com.example.appsanst.ressources.RepasAdapter;

import java.util.ArrayList;
import java.util.List;

public class RepasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RepasAdapter adapter;
    private List<Repas> repasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repas); // Assure-toi que le nom est correct

        recyclerView = findViewById(R.id.recyclerViewRepas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ⚠️ Exemple de données temporaires :
        repasList = new ArrayList<>();
        repasList.add(new Repas("Petit-déjeuner", 350, 45, 15, 10));
        repasList.add(new Repas("Déjeuner", 700, 80, 35, 25));
        repasList.add(new Repas("Dîner", 600, 70, 30, 20));

        adapter = new RepasAdapter(repasList);
        recyclerView.setAdapter(adapter);
    }
}

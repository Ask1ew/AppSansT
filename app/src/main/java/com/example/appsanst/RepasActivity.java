package com.example.appsanst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsanst.ressources.Repas;
import com.example.appsanst.ressources.RepasAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RepasActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_AJOUT_REPAS = 100;

    private RecyclerView recyclerView;
    private RepasAdapter adapter;
    private List<Repas> repasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repas);

        recyclerView = findViewById(R.id.recyclerViewRepas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repasList = new ArrayList<>();
        adapter = new RepasAdapter(repasList);
        recyclerView.setAdapter(adapter);

        // Bouton flottant pour ajouter un repas
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, AjoutRepasActivity.class);
            startActivityForResult(intent, REQUEST_CODE_AJOUT_REPAS);
        });

        // Optionnel : ajouter ici chargement des repas depuis Room ou SharedPreferences si tu veux plus tard
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_AJOUT_REPAS && resultCode == Activity.RESULT_OK) {
            String nom = data.getStringExtra("nom");
            int cal = data.getIntExtra("calories", 0);
            int gluc = data.getIntExtra("glucides", 0);
            int prot = data.getIntExtra("proteines", 0);
            int lip = data.getIntExtra("lipides", 0);

            Repas nouveauRepas = new Repas(nom, cal, gluc, prot, lip);
            repasList.add(nouveauRepas);
            adapter.notifyItemInserted(repasList.size() - 1);

            Toast.makeText(this, "Repas ajouté : " + nom, Toast.LENGTH_SHORT).show();
        }
    }
}

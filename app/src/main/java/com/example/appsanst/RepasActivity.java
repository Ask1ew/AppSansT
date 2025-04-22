package com.example.appsanst;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
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
    private static final int REQUEST_CODE_OBJECTIFS  = 101;

    /* vues */
    private RecyclerView recyclerView;
    private ProgressBar progressBarCalories, progressBarProteines,
            progressBarLipides,  progressBarGlucides;

    /* données */
    private final List<Repas> repasList = new ArrayList<>();
    private RepasAdapter adapter;

    /* objectifs */
    private int objectifCalories, objectifProteines, objectifLipides, objectifGlucides;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repas);

        /* ==== vues ==== */
        recyclerView         = findViewById(R.id.recyclerViewRepas);
        progressBarCalories  = findViewById(R.id.progressBarCalories);
        progressBarProteines = findViewById(R.id.progressBarProteines);
        progressBarLipides   = findViewById(R.id.progressBarLipides);
        progressBarGlucides  = findViewById(R.id.progressBarGlucides);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RepasAdapter(repasList);
        recyclerView.setAdapter(adapter);

        /* ==== objectifs ==== */
        preferences = getSharedPreferences("objectifs_nutritionnels", MODE_PRIVATE);
        loadObjectives();
        setProgressBarMax();

        /* ==== Ajout repas ==== */
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v ->
                startActivityForResult(
                        new Intent(this, AjoutRepasActivity.class),
                        REQUEST_CODE_AJOUT_REPAS));

        updateProgressBars();      // affichage initial à 0/objectif
    }

    /** charge les objectifs depuis SharedPreferences (valeurs par défaut si absent) */
    private void loadObjectives() {
        objectifCalories  = preferences.getInt("objectifCalories", 2000);
        objectifProteines = preferences.getInt("objectifProteines", 100);
        objectifLipides   = preferences.getInt("objectifLipides", 70);
        objectifGlucides  = preferences.getInt("objectifGlucides", 250);
    }

    /** applique les objectifs comme valeur max des ProgressBars */
    private void setProgressBarMax() {
        progressBarCalories .setMax(objectifCalories);
        progressBarProteines.setMax(objectifProteines);
        progressBarLipides  .setMax(objectifLipides);
        progressBarGlucides .setMax(objectifGlucides);
    }

    /** === RÉSULTATS de AjoutRepasActivity et ObjectifsActivity === */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null) return;

        if (requestCode == REQUEST_CODE_AJOUT_REPAS) {            // nouveau repas
            Repas r = new Repas(
                    data.getStringExtra("nomRepas"),
                    data.getIntExtra("calories", 0),
                    data.getIntExtra("glucides", 0),
                    data.getIntExtra("proteines", 0),
                    data.getIntExtra("lipides", 0));
            repasList.add(r);
            adapter.notifyItemInserted(repasList.size() - 1);
            updateProgressBars();
            Toast.makeText(this, "Repas ajouté : " + r.getNom(), Toast.LENGTH_SHORT).show();

        } else if (requestCode == REQUEST_CODE_OBJECTIFS) {       // objectifs modifiés
            objectifCalories  = data.getIntExtra("objectifCalories",  objectifCalories);
            objectifProteines = data.getIntExtra("objectifProteines", objectifProteines);
            objectifLipides   = data.getIntExtra("objectifLipides",   objectifLipides);
            objectifGlucides  = data.getIntExtra("objectifGlucides",  objectifGlucides);

            // sauvegarde
            preferences.edit()
                    .putInt("objectifCalories",  objectifCalories)
                    .putInt("objectifProteines", objectifProteines)
                    .putInt("objectifLipides",   objectifLipides)
                    .putInt("objectifGlucides",  objectifGlucides)
                    .apply();

            setProgressBarMax();
            updateProgressBars();
            Toast.makeText(this, "Objectifs nutritionnels mis à jour", Toast.LENGTH_SHORT).show();
        }
    }

    /** calcule les totaux consommés et met à jour les ProgressBars */
    private void updateProgressBars() {
        int totalCal = 0, totalProt = 0, totalLip = 0, totalGlu = 0;
        for (Repas r : repasList) {
            totalCal  += r.getCalories();
            totalProt += r.getProteines();
            totalLip  += r.getLipides();
            totalGlu  += r.getGlucides();
        }
        progressBarCalories .setProgress(Math.min(totalCal,  objectifCalories));
        progressBarProteines.setProgress(Math.min(totalProt, objectifProteines));
        progressBarLipides  .setProgress(Math.min(totalLip,  objectifLipides));
        progressBarGlucides .setProgress(Math.min(totalGlu,  objectifGlucides));
    }

    /* === menu « objectifs » dans la barre d’applis === */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repas, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_objectifs) {
            Intent i = new Intent(this, ObjectifsActivity.class);
            i.putExtra("calories",  objectifCalories);
            i.putExtra("proteines", objectifProteines);
            i.putExtra("lipides",   objectifLipides);
            i.putExtra("glucides",  objectifGlucides);
            startActivityForResult(i, REQUEST_CODE_OBJECTIFS);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

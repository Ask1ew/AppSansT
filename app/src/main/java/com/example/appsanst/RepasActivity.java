package com.example.appsanst;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import database.DatabaseHelper;
import com.example.appsanst.ressources.Repas;
import com.example.appsanst.ressources.RepasAdapter;
import com.example.appsanst.ui.AddFabFragment;

import java.util.ArrayList;
import java.util.List;

public class RepasActivity extends AppCompatActivity
        implements AddFabFragment.OnFabAction {

    private static final int REQ_AJOUT_REPAS = 100;
    private static final int REQ_OBJECTIFS = 101;

    private RecyclerView recyclerView;
    private ProgressBar progressCalories, progressProteines,
            progressLipides, progressGlucides;

    private final List<Repas> repasList = new ArrayList<>();
    private RepasAdapter adapter;

    private int objCalories, objProteines, objLipides, objGlucides;
    private String userId;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repas);

        // Récupérer l'ID utilisateur
        SharedPreferences sessionPrefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = sessionPrefs.getString("user_id", null);

        if (userId == null) {
            Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialiser la base de données
        dbHelper = DatabaseHelper.getInstance(this);

        // Binding des vues
        recyclerView = findViewById(R.id.recyclerViewRepas);
        progressCalories = findViewById(R.id.progressBarCalories);
        progressProteines = findViewById(R.id.progressBarProteines);
        progressLipides = findViewById(R.id.progressBarLipides);
        progressGlucides = findViewById(R.id.progressBarGlucides);

        // Configurer RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RepasAdapter(repasList);
        recyclerView.setAdapter(adapter);

        // Charger les repas de l'utilisateur
        chargerRepas();

        // Charger les objectifs depuis la base de données
        chargerObjectifs();

        // FAB fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fab_fragment_container,
                        AddFabFragment.newInstance(AddFabFragment.ACTION_ADD_MEAL))
                .commit();
    }

    private void chargerRepas() {
        // Vider la liste actuelle
        repasList.clear();

        // Charger depuis la base de données
        repasList.addAll(dbHelper.getRepasUtilisateur(userId));

        // Mettre à jour l'adaptateur
        adapter.notifyDataSetChanged();

        // Mettre à jour les barres de progression
        updateProgressBars();
    }

    private void chargerObjectifs() {
        int[] objectifs = dbHelper.getObjectifsNutritionnels(userId);
        objCalories = objectifs[0];
        objProteines = objectifs[1];
        objLipides = objectifs[2];
        objGlucides = objectifs[3];

        setProgressBarMax();
        updateProgressBars();
    }

    @Override
    public void onFabAction(@NonNull String actionTag) {
        if (AddFabFragment.ACTION_ADD_MEAL.equals(actionTag)) {
            startActivityForResult(
                    new Intent(this, AjoutRepasActivity.class),
                    REQ_AJOUT_REPAS);
        }
    }

    private void setProgressBarMax() {
        progressCalories.setMax(objCalories);
        progressProteines.setMax(objProteines);
        progressLipides.setMax(objLipides);
        progressGlucides.setMax(objGlucides);
    }

    private void updateProgressBars() {
        int totCal = 0, totProt = 0, totLip = 0, totGlu = 0;
        for (Repas r : repasList) {
            totCal += r.getCalories();
            totProt += r.getProteines();
            totLip += r.getLipides();
            totGlu += r.getGlucides();
        }
        progressCalories.setProgress(Math.min(totCal, objCalories));
        progressProteines.setProgress(Math.min(totProt, objProteines));
        progressLipides.setProgress(Math.min(totLip, objLipides));
        progressGlucides.setProgress(Math.min(totGlu, objGlucides));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) return;

        if (requestCode == REQ_AJOUT_REPAS) {
            Repas repas = new Repas(
                    data.getStringExtra("nomRepas"),
                    data.getIntExtra("calories", 0),
                    data.getIntExtra("glucides", 0),
                    data.getIntExtra("proteines", 0),
                    data.getIntExtra("lipides", 0));

            // Sauvegarder dans la base de données
            dbHelper.ajouterRepas(userId, repas);

            // Ajouter à la liste locale
            repasList.add(0, repas);
            adapter.notifyItemInserted(0);
            updateProgressBars();

            Toast.makeText(this, "Repas ajouté : " + repas.getNom(), Toast.LENGTH_SHORT).show();

        } else if (requestCode == REQ_OBJECTIFS) {
            objCalories = data.getIntExtra("objectifCalories", objCalories);
            objProteines = data.getIntExtra("objectifProteines", objProteines);
            objLipides = data.getIntExtra("objectifLipides", objLipides);
            objGlucides = data.getIntExtra("objectifGlucides", objGlucides);

            // Sauvegarder dans la base de données
            dbHelper.sauvegarderObjectifs(userId, objCalories, objProteines, objLipides, objGlucides);

            setProgressBarMax();
            updateProgressBars();
            Toast.makeText(this, "Objectifs mis à jour", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_objectifs) {
            Intent i = new Intent(this, ObjectifsActivity.class);
            i.putExtra("objectifCalories", objCalories);
            i.putExtra("objectifProteines", objProteines);
            i.putExtra("objectifLipides", objLipides);
            i.putExtra("objectifGlucides", objGlucides);
            startActivityForResult(i, REQ_OBJECTIFS);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

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

import com.example.appsanst.ressources.Repas;
import com.example.appsanst.ressources.RepasAdapter;
import com.example.appsanst.ui.AddFabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Écran « Repas » :
 *  • liste des repas consommés                     (RecyclerView)
 *  • suivi journalier Calories / P / L / G         (4 ProgressBar)
 *  • FAB (fragment) pour ajouter un repas          (+ réutilisable ailleurs)
 *  • menu d’accès à l’écran « Objectifs »
 */
public class RepasActivity extends AppCompatActivity
        implements AddFabFragment.OnFabAction {

    /* request codes */
    private static final int REQ_AJOUT_REPAS  = 100;
    private static final int REQ_OBJECTIFS    = 101;

    /* vues */
    private RecyclerView recyclerView;
    private ProgressBar progressCalories, progressProteines,
            progressLipides,  progressGlucides;

    /* données */
    private final List<Repas> repasList = new ArrayList<>();
    private RepasAdapter adapter;

    /* objectifs (valeurs max) */
    private int objCalories, objProteines, objLipides, objGlucides;
    private SharedPreferences prefs;

    /* -------------------------------------------------------------------- */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repas);

        /* --- binding vues ------------------------------------------------ */
        recyclerView      = findViewById(R.id.recyclerViewRepas);
        progressCalories  = findViewById(R.id.progressBarCalories);
        progressProteines = findViewById(R.id.progressBarProteines);
        progressLipides   = findViewById(R.id.progressBarLipides);
        progressGlucides  = findViewById(R.id.progressBarGlucides);

        /* --- liste & adapter -------------------------------------------- */
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RepasAdapter(repasList);
        recyclerView.setAdapter(adapter);

        /* --- objectifs --------------------------------------------------- */
        prefs = getSharedPreferences("objectifs_nutritionnels", MODE_PRIVATE);
        loadObjectives();          // -> objCalories, objProteines, …
        setProgressBarMax();       // applique aux 4 barres
        updateProgressBars();      // valeur initiale 0 / max

        /* --- FAB fragment ----------------------------------------------- */
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fab_fragment_container,
                        AddFabFragment.newInstance(AddFabFragment.ACTION_ADD_MEAL))
                .commit();
    }

    /* -------------------------------------------------------------------- */
    /*  callback envoyé par AddFabFragment */
    @Override public void onFabAction(@NonNull String actionTag) {
        if (AddFabFragment.ACTION_ADD_MEAL.equals(actionTag)) {
            startActivityForResult(
                    new Intent(this, AjoutRepasActivity.class),
                    REQ_AJOUT_REPAS);
        }
        // d’autres actions (poids, etc.) pourront être testées ici
    }

    /* -------------------------------------------------------------------- */
    /** lecture des objectifs depuis SharedPreferences (avec défauts) */
    private void loadObjectives() {
        objCalories  = prefs.getInt("objectifCalories",  2000);
        objProteines = prefs.getInt("objectifProteines", 100);
        objLipides   = prefs.getInt("objectifLipides",    70);
        objGlucides  = prefs.getInt("objectifGlucides",  250);
    }

    /** applique obj* comme max des ProgressBar */
    private void setProgressBarMax() {
        progressCalories .setMax(objCalories);
        progressProteines.setMax(objProteines);
        progressLipides  .setMax(objLipides);
        progressGlucides .setMax(objGlucides);
    }

    /** recalcule les apports journaliers et met à jour les ProgressBar */
    private void updateProgressBars() {
        int totCal = 0, totProt = 0, totLip = 0, totGlu = 0;
        for (Repas r : repasList) {
            totCal  += r.getCalories();
            totProt += r.getProteines();
            totLip  += r.getLipides();
            totGlu  += r.getGlucides();
        }
        progressCalories .setProgress(Math.min(totCal,  objCalories));
        progressProteines.setProgress(Math.min(totProt, objProteines));
        progressLipides  .setProgress(Math.min(totLip,  objLipides));
        progressGlucides .setProgress(Math.min(totGlu,  objGlucides));
    }

    /* -------------------------------------------------------------------- */
    /** résultat de AjoutRepasActivity ou ObjectifsActivity */
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
            repasList.add(repas);
            adapter.notifyItemInserted(repasList.size() - 1);
            updateProgressBars();
            Toast.makeText(this, "Repas ajouté : " + repas.getNom(), Toast.LENGTH_SHORT).show();

        } else if (requestCode == REQ_OBJECTIFS) {
            objCalories  = data.getIntExtra("objectifCalories",  objCalories);
            objProteines = data.getIntExtra("objectifProteines", objProteines);
            objLipides   = data.getIntExtra("objectifLipides",   objLipides);
            objGlucides  = data.getIntExtra("objectifGlucides",  objGlucides);

            prefs.edit()
                    .putInt("objectifCalories",  objCalories)
                    .putInt("objectifProteines", objProteines)
                    .putInt("objectifLipides",   objLipides)
                    .putInt("objectifGlucides",  objGlucides)
                    .apply();

            setProgressBarMax();
            updateProgressBars();
            Toast.makeText(this, "Objectifs mis à jour", Toast.LENGTH_SHORT).show();
        }
    }

    /* -------------------------------------------------------------------- */
    /* menu option « Objectifs » */
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repas, menu);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_objectifs) {
            Intent i = new Intent(this, ObjectifsActivity.class);
            i.putExtra("calories",  objCalories);
            i.putExtra("proteines", objProteines);
            i.putExtra("lipides",   objLipides);
            i.putExtra("glucides",  objGlucides);
            startActivityForResult(i, REQ_OBJECTIFS);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

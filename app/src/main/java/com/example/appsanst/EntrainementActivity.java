package com.example.appsanst;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.ressources.CycleEntrainement;
import com.example.appsanst.ressources.CycleAdapter;
import com.example.appsanst.ui.AddFabFragment;
import java.util.List;

public class EntrainementActivity extends AppCompatActivity implements AddFabFragment.OnFabAction {
    private static final int REQ_AJOUT_ENTRAINEMENT = 200;
    private RecyclerView recyclerView;
    private List<CycleEntrainement> cycles;
    private CycleAdapter cycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrainement);

        // Préparation de la liste des cycles d'entraînement (préconfigurés + existants)
        cycles = CycleEntrainement.allCyclesList;
        recyclerView = findViewById(R.id.recyclerView_cycles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cycleAdapter = new CycleAdapter(cycles);
        recyclerView.setAdapter(cycleAdapter);

        // Insertion du FAB fragment pour l'ajout d'un entraînement
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fab_fragment_container,
                        AddFabFragment.newInstance("addTraining"))
                .commit();
    }

    // Gestion du clic sur le FAB (AddFabFragment)
    @Override
    public void onFabAction(@NonNull String actionTag) {
        if ("addTraining".equals(actionTag)) {
            // Lancement de l'activité d'ajout d'entraînement
            Intent intent = new Intent(this, AjoutEntrainementActivity.class);
            startActivityForResult(intent, REQ_AJOUT_ENTRAINEMENT);
        }
    }

    // Récupération du résultat de AjoutEntrainementActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) return;
        if (requestCode == REQ_AJOUT_ENTRAINEMENT) {
            CycleEntrainement nouveauCycle = (CycleEntrainement) data.getSerializableExtra("nouveauCycle");
            if (nouveauCycle != null) {
                // Ajouter le nouveau cycle à la liste en mémoire et mettre à jour l'affichage
                cycles.add(nouveauCycle);
                cycleAdapter.notifyItemInserted(cycles.size() - 1);
                Toast.makeText(this, "Entraînement ajouté : " + nouveauCycle.getNom(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

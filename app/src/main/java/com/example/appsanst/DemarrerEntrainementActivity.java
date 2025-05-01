package com.example.appsanst;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import com.example.appsanst.ressources.CycleEntrainement;
import java.util.List;

public class DemarrerEntrainementActivity extends AppCompatActivity {
    private Button btnStartEmpty, btnStartSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demarrer_entrainement);

        btnStartEmpty = findViewById(R.id.button_start_empty);
        btnStartSaved = findViewById(R.id.button_start_saved);

        // Démarrer un entraînement vide
        btnStartEmpty.setOnClickListener(v -> {
            Intent intent = new Intent(this, SessionEntrainementActivity.class);
            // Aucune donnée supplémentaire : démarrage en mode "vide"
            startActivity(intent);
        });

        // Démarrer un entraînement enregistré
        btnStartSaved.setOnClickListener(v -> {
            List<CycleEntrainement> cycles = CycleEntrainement.allCyclesList;
            if (cycles.isEmpty()) {
                Toast.makeText(this, "Aucun entraînement disponible", Toast.LENGTH_SHORT).show();
                return;
            }
            // Préparer la liste des noms d'entraînements pour le dialogue de sélection
            String[] nomsCycles = new String[cycles.size()];
            for (int i = 0; i < cycles.size(); i++) {
                nomsCycles[i] = cycles.get(i).getNom();
            }
            // Créer une boîte de dialogue pour choisir l'entraînement
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choisissez un entraînement")
                    .setItems(nomsCycles, (dialog, which) -> {
                        // Lancement de la session avec le cycle sélectionné
                        CycleEntrainement choisi = cycles.get(which);
                        Intent intent = new Intent(this, SessionEntrainementActivity.class);
                        intent.putExtra("cycleChoisi", choisi);
                        startActivity(intent);
                    })
                    .setNegativeButton("Annuler", null);
            builder.create().show();
        });
    }
}

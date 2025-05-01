package com.example.appsanst;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fragments.AddWeightFragment;
import database.PoidsDbHelper;
import com.example.appsanst.ressources.PoidsAdapter;
import com.example.appsanst.ressources.PoidsEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PoidsActivity extends AppCompatActivity implements AddWeightFragment.OnAddWeightListener {

    private List<PoidsEntry> poidsEntries = new ArrayList<>();
    private PoidsAdapter adapter;
    private RecyclerView recyclerView;
    private PoidsDbHelper dbHelper;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poids);

        // Récupérer l'ID utilisateur depuis les préférences
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = prefs.getString("user_id", "default_user");

        // Initialiser la base de données
        dbHelper = new PoidsDbHelper(this);

        // Charger les données depuis la base
        poidsEntries = dbHelper.getPoidsEntries(userId);

        // Configurer le RecyclerView
        recyclerView = findViewById(R.id.recyclerView_poids);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PoidsAdapter(poidsEntries);
        recyclerView.setAdapter(adapter);

        // Ajouter le fragment avec le FAB
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fab_container, new AddWeightFragment())
                    .commit();
        }
    }

    @Override
    public void onAddWeight() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_poids, null);
        EditText editPoids = dialogView.findViewById(R.id.editText_poids);

        builder.setTitle("Ajouter un poids")
                .setView(dialogView)
                .setPositiveButton("Enregistrer", (dialog, id) -> {
                    try {
                        String strPoids = editPoids.getText().toString();
                        if (!strPoids.isEmpty()) {
                            float poids = Float.parseFloat(strPoids);
                            ajouterPoids(poids);
                        } else {
                            Toast.makeText(this, "Veuillez entrer un poids", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Valeur invalide", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void ajouterPoids(float poids) {
        Date date = new Date();

        // Créer une nouvelle entrée
        PoidsEntry nouvelleEntree = new PoidsEntry(date, poids);

        // Sauvegarder dans la base de données
        long id = dbHelper.ajouterPoids(userId, date, poids);

        if (id != -1) {
            // Ajouter à la liste et mettre à jour l'UI
            poidsEntries.add(0, nouvelleEntree);
            adapter.notifyItemInserted(0);
            recyclerView.smoothScrollToPosition(0);
            Toast.makeText(this, "Poids ajouté : " + poids + " kg", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
        }
    }
}

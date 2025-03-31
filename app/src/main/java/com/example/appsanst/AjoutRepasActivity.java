package com.example.appsanst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsanst.ressources.Aliment;
import com.example.appsanst.ressources.AlimentAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AjoutRepasActivity extends AppCompatActivity {

    private TextInputEditText inputNomAliment, inputQuantite, inputCalories;
    private AutoCompleteTextView dropdownUnite;
    private RecyclerView recyclerView;
    private AlimentAdapter adapter;
    private List<Aliment> listeAliments;
    private MaterialButton btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_repas);

        inputNomAliment = findViewById(R.id.inputNomAliment);
        inputQuantite = findViewById(R.id.inputQuantite);
        inputCalories = findViewById(R.id.inputCalories);
        dropdownUnite = findViewById(R.id.dropdownUnite);
        recyclerView = findViewById(R.id.recyclerViewAliments);
        btnValider = findViewById(R.id.btnValiderRepas);

        listeAliments = new ArrayList<>();
        adapter = new AlimentAdapter(listeAliments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup de la liste déroulante des unités
        String[] unites = new String[] { "g", "ml", "pièce", "cuillère", "tasse" };
        ArrayAdapter<String> adapterUnite = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, unites);
        dropdownUnite.setAdapter(adapterUnite);

        // Événements
        findViewById(R.id.btnAjouterAliment).setOnClickListener(v -> ajouterAliment());
        btnValider.setOnClickListener(v -> validerRepas());
    }

    private void ajouterAliment() {
        String nom = inputNomAliment.getText().toString().trim();
        String quantite = inputQuantite.getText().toString().trim();
        String calStr = inputCalories.getText().toString().trim();
        String unite = dropdownUnite.getText().toString().trim();

        // Vérification des champs
        if (TextUtils.isEmpty(nom) || TextUtils.isEmpty(quantite) || TextUtils.isEmpty(unite) || TextUtils.isEmpty(calStr)) {
            Toast.makeText(this, "Remplis tous les champs pour l'aliment", Toast.LENGTH_SHORT).show();
            return;
        }

        // Conversion
        int calories = Integer.parseInt(calStr);
        String quantiteComplete = quantite + " " + unite;

        // Ajout à la liste
        listeAliments.add(new Aliment(nom, quantiteComplete, calories));
        adapter.notifyItemInserted(listeAliments.size() - 1);

        // Reset des champs
        inputNomAliment.setText("");
        inputQuantite.setText("");
        inputCalories.setText("");
        dropdownUnite.setText("");
    }

    private void validerRepas() {
        if (listeAliments.isEmpty()) {
            Toast.makeText(this, "Ajoute au moins un aliment", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalCalories = 0;
        for (Aliment a : listeAliments) {
            totalCalories += a.getCalories();
        }

        Intent result = new Intent();
        result.putExtra("nom", "Repas personnalisé");
        result.putExtra("calories", totalCalories);
        result.putExtra("glucides", 0);   // Tu pourras calculer ça plus tard
        result.putExtra("proteines", 0);
        result.putExtra("lipides", 0);

        setResult(Activity.RESULT_OK, result);
        finish();
    }
}

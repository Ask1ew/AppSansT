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

    private TextInputEditText inputNomRepas;
    private TextInputEditText inputNomAliment, inputQuantite, inputCalories,
            inputGlucides, inputProteines, inputLipides;
    private AutoCompleteTextView dropdownUnite;
    private RecyclerView recyclerView;
    private AlimentAdapter adapter;
    private List<Aliment> listeAliments;
    private MaterialButton btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_repas);

        inputNomRepas  = findViewById(R.id.inputNomRepas);
        inputNomAliment = findViewById(R.id.inputNomAliment);
        inputQuantite = findViewById(R.id.inputQuantite);
        inputCalories = findViewById(R.id.inputCalories);
        inputGlucides   = findViewById(R.id.inputGlucides);
        inputProteines  = findViewById(R.id.inputProteines);
        inputLipides    = findViewById(R.id.inputLipides);
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
        String nom      = inputNomAliment.getText().toString().trim();
        String quantite = inputQuantite.getText().toString().trim();
        String unite    = dropdownUnite.getText().toString().trim();

        // chaînes pour conversion
        String calStr = inputCalories.getText().toString().trim();
        String gluStr = inputGlucides.getText().toString().trim();
        String proStr = inputProteines.getText().toString().trim();
        String lipStr = inputLipides.getText().toString().trim();

        if (TextUtils.isEmpty(nom) || TextUtils.isEmpty(quantite) || TextUtils.isEmpty(unite)
                || TextUtils.isEmpty(calStr) || TextUtils.isEmpty(gluStr)
                || TextUtils.isEmpty(proStr) || TextUtils.isEmpty(lipStr)) {
            Toast.makeText(this, "Remplis tous les champs pour l'aliment", Toast.LENGTH_SHORT).show();
            return;
        }

        int calories  = Integer.parseInt(calStr);
        int glucides  = Integer.parseInt(gluStr);
        int proteines = Integer.parseInt(proStr);
        int lipides   = Integer.parseInt(lipStr);

        String quantiteComplete = quantite + " " + unite;

        listeAliments.add(
                new Aliment(nom, quantiteComplete, calories, glucides, proteines, lipides)
        );
        adapter.notifyItemInserted(listeAliments.size() - 1);

        // reset
        inputNomAliment.setText("");
        inputQuantite.setText("");
        inputCalories.setText("");
        inputGlucides.setText("");
        inputProteines.setText("");
        inputLipides.setText("");
        dropdownUnite.setText("");
    }

    private void validerRepas() {

        String nomRepas = inputNomRepas.getText().toString().trim();
        if (TextUtils.isEmpty(nomRepas)) {
            Toast.makeText(this, "Indique le nom du repas", Toast.LENGTH_SHORT).show();
            return;
        }

        if (listeAliments.isEmpty()) {
            Toast.makeText(this, "Ajoute au moins un aliment", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalCal=0, totalGlu=0, totalPro=0, totalLip=0;
        for (Aliment a : listeAliments) {
            totalCal += a.getCalories();
            totalGlu += a.getGlucides();
            totalPro += a.getProteines();
            totalLip += a.getLipides();
        }

        Intent result = new Intent();
        result.putExtra("nomRepas",  nomRepas);
        result.putExtra("calories",  totalCal);
        result.putExtra("glucides",  totalGlu);
        result.putExtra("proteines", totalPro);
        result.putExtra("lipides",   totalLip);

        setResult(Activity.RESULT_OK, result);
        finish();
    }
}

package com.example.appsanst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AjoutRepasActivity extends AppCompatActivity {

    private TextInputEditText inputNom, inputCalories, inputGlucides, inputProteines, inputLipides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_repas);

        inputNom = findViewById(R.id.inputNom);
        inputCalories = findViewById(R.id.inputCalories);
        inputGlucides = findViewById(R.id.inputGlucides);
        inputProteines = findViewById(R.id.inputProteines);
        inputLipides = findViewById(R.id.inputLipides);
        MaterialButton btnValider = findViewById(R.id.btnValider);

        btnValider.setOnClickListener(v -> validerEtRetourner());
    }

    private void validerEtRetourner() {
        String nom = inputNom.getText().toString();
        String strCal = inputCalories.getText().toString();
        String strGluc = inputGlucides.getText().toString();
        String strProt = inputProteines.getText().toString();
        String strLip = inputLipides.getText().toString();

        if (TextUtils.isEmpty(strCal) || TextUtils.isEmpty(strGluc)
                || TextUtils.isEmpty(strProt) || TextUtils.isEmpty(strLip)) {
            Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        int cal = Integer.parseInt(strCal);
        int gluc = Integer.parseInt(strGluc);
        int prot = Integer.parseInt(strProt);
        int lip = Integer.parseInt(strLip);

        if (TextUtils.isEmpty(nom)) nom = "Repas";

        // On retourne les données à l’activité précédente
        Intent result = new Intent();
        result.putExtra("nom", nom);
        result.putExtra("calories", cal);
        result.putExtra("glucides", gluc);
        result.putExtra("proteines", prot);
        result.putExtra("lipides", lip);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}

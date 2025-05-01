package com.example.appsanst;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import database.DatabaseHelper;
import com.example.appsanst.ressources.Objectif;
import com.example.appsanst.ressources.ObjectifAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Arrays;
import java.util.List;

public class ObjectifsActivity extends AppCompatActivity {

    private TextInputEditText inCal, inProt, inLip, inGlu;
    private String userId;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectifs);

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

        // Partie formulaire nutritionnel
        inCal = findViewById(R.id.inputObjCalories);
        inProt = findViewById(R.id.inputObjProteines);
        inLip = findViewById(R.id.inputObjLipides);
        inGlu = findViewById(R.id.inputObjGlucides);

        // Récupérer les valeurs soit depuis l'intent, soit depuis la base de données
        Intent src = getIntent();
        if (src.hasExtra("objectifCalories")) {
            inCal.setText(String.valueOf(src.getIntExtra("objectifCalories", 2000)));
            inProt.setText(String.valueOf(src.getIntExtra("objectifProteines", 100)));
            inLip.setText(String.valueOf(src.getIntExtra("objectifLipides", 70)));
            inGlu.setText(String.valueOf(src.getIntExtra("objectifGlucides", 250)));
        } else {
            // Charger depuis la base de données
            int[] objectifs = dbHelper.getObjectifsNutritionnels(userId);
            inCal.setText(String.valueOf(objectifs[0]));
            inProt.setText(String.valueOf(objectifs[1]));
            inLip.setText(String.valueOf(objectifs[2]));
            inGlu.setText(String.valueOf(objectifs[3]));
        }

        MaterialButton btnSave = findViewById(R.id.btnSaveObjectifs);
        btnSave.setOnClickListener(v -> save());

        // Partie historique des autres objectifs
        List<Objectif> objectifs = Arrays.asList(
                new Objectif("Poids", "Atteindre votre poids idéal", 70, 65),
                new Objectif("Pas quotidiens", "Objectif de marche quotidienne", 10000, 7500),
                new Objectif("Hydratation", "Verres d'eau par jour", 8, 5)
        );

        RecyclerView recyclerView = findViewById(R.id.recyclerView_objectifs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ObjectifAdapter(objectifs));
    }

    private void save() {
        if (TextUtils.isEmpty(inCal.getText()) ||
                TextUtils.isEmpty(inProt.getText()) ||
                TextUtils.isEmpty(inLip.getText()) ||
                TextUtils.isEmpty(inGlu.getText())) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Récupérer les valeurs
        int calories = Integer.parseInt(inCal.getText().toString());
        int proteines = Integer.parseInt(inProt.getText().toString());
        int lipides = Integer.parseInt(inLip.getText().toString());
        int glucides = Integer.parseInt(inGlu.getText().toString());

        // Sauvegarder dans la base de données
        dbHelper.sauvegarderObjectifs(userId, calories, proteines, lipides, glucides);

        // Préparer le résultat pour l'activité appelante
        Intent data = new Intent();
        data.putExtra("objectifCalories", calories);
        data.putExtra("objectifProteines", proteines);
        data.putExtra("objectifLipides", lipides);
        data.putExtra("objectifGlucides", glucides);

        setResult(Activity.RESULT_OK, data);
        finish();
    }
}

package com.example.appsanst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.ressources.Exercice;
import com.example.appsanst.ressources.CycleEntrainement;
import com.example.appsanst.ressources.ExerciceAdapter;  // nouvel adaptateur
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class AjoutEntrainementActivity extends AppCompatActivity {
    // Champs de saisie pour l'entraînement et les exercices
    private TextInputEditText inputNomEntrainement;
    private TextInputEditText inputNomExercice, inputSeries, inputRepetitions, inputPoids, inputRepos;
    private RecyclerView recyclerView;
    private ExerciceAdapter adapter;
    private List<Exercice> listeExercices;
    private MaterialButton btnAjouterExercice, btnValiderEntrainement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_entrainement);

        // Liaison des vues
        inputNomEntrainement = findViewById(R.id.inputNomEntrainement);
        inputNomExercice    = findViewById(R.id.inputNomExercice);
        inputSeries         = findViewById(R.id.inputSeries);
        inputRepetitions    = findViewById(R.id.inputRepetitions);
        inputPoids          = findViewById(R.id.inputPoids);
        inputRepos          = findViewById(R.id.inputRepos);
        recyclerView        = findViewById(R.id.recyclerViewExercices);
        btnAjouterExercice  = findViewById(R.id.btnAjouterExercice);
        btnValiderEntrainement = findViewById(R.id.btnValiderEntrainement);

        // Initialisation de la liste des exercices et de son adaptateur
        listeExercices = new ArrayList<>();
        adapter = new ExerciceAdapter(listeExercices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Ajout d'un exercice à la liste lorsqu'on clique sur "Ajouter l'exercice"
        btnAjouterExercice.setOnClickListener(v -> ajouterExercice());

        // Validation de l'entraînement lorsqu'on clique sur "Valider l'entraînement"
        btnValiderEntrainement.setOnClickListener(v -> validerEntrainement());
    }

    private void ajouterExercice() {
        // Récupération et nettoyage des valeurs saisies
        String nomEx = inputNomExercice.getText().toString().trim();
        String seriesStr = inputSeries.getText().toString().trim();
        String repsStr   = inputRepetitions.getText().toString().trim();
        String poidsStr  = inputPoids.getText().toString().trim();
        String reposStr  = inputRepos.getText().toString().trim();

        // Validation des champs requis pour l'exercice
        if (TextUtils.isEmpty(nomEx) || TextUtils.isEmpty(seriesStr)
                || TextUtils.isEmpty(repsStr) || TextUtils.isEmpty(reposStr)) {
            Toast.makeText(this, "Veuillez remplir le nom, le nombre de séries, de répétitions et le repos",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        int series = Integer.parseInt(seriesStr);
        int repetitions = Integer.parseInt(repsStr);
        int repos = Integer.parseInt(reposStr);
        // Poids optionnel (0 si non renseigné)
        int poids = 0;
        if (!TextUtils.isEmpty(poidsStr)) {
            poids = Integer.parseInt(poidsStr);
        }

        // Création de l'objet Exercice et ajout à la liste
        Exercice nouvelEx = new Exercice(nomEx, series, repetitions, repos, poids);
        listeExercices.add(nouvelEx);
        adapter.notifyItemInserted(listeExercices.size() - 1);

        // Réinitialisation des champs de saisie pour le prochain exercice
        inputNomExercice.setText("");
        inputSeries.setText("");
        inputRepetitions.setText("");
        inputPoids.setText("");
        inputRepos.setText("");
    }

    private void validerEntrainement() {
        String nomEntrainement = inputNomEntrainement.getText().toString().trim();
        if (TextUtils.isEmpty(nomEntrainement)) {
            Toast.makeText(this, "Veuillez indiquer un nom pour l'entraînement", Toast.LENGTH_SHORT).show();
            return;
        }
        if (listeExercices.isEmpty()) {
            Toast.makeText(this, "Ajoutez au moins un exercice à l'entraînement", Toast.LENGTH_SHORT).show();
            return;
        }
        // Création du cycle d'entraînement contenant les exercices saisis
        CycleEntrainement cycle = new CycleEntrainement(nomEntrainement, listeExercices);
        // Retour du nouvel objet CycleEntrainement à l'activité appelante
        Intent data = new Intent();
        data.putExtra("nouveauCycle", cycle);
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}

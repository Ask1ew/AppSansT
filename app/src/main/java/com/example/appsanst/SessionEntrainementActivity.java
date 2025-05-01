package com.example.appsanst;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Chronometer;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.ressources.CycleEntrainement;
import com.example.appsanst.ressources.Exercice;
import com.example.appsanst.ressources.ExerciceAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class SessionEntrainementActivity extends AppCompatActivity {
    private Chronometer chronometre;
    private MaterialButton btnTerminer;
    private TextInputEditText inputNomEx_session, inputSeries_session, inputReps_session, inputPoids_session, inputRepos_session;
    private MaterialButton btnAjouterEx_session;
    private RecyclerView recyclerView;
    private ExerciceAdapter adapter;
    private List<Exercice> exercicesSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_entrainement);

        // Liaison des vues
        chronometre = findViewById(R.id.chronometre);
        btnTerminer = findViewById(R.id.button_finish_session);
        recyclerView = findViewById(R.id.recyclerView_session_exercices);
        inputNomEx_session   = findViewById(R.id.inputNomExercice_session);
        inputSeries_session  = findViewById(R.id.inputSeries_session);
        inputReps_session    = findViewById(R.id.inputRepetitions_session);
        inputPoids_session   = findViewById(R.id.inputPoids_session);
        inputRepos_session   = findViewById(R.id.inputRepos_session);
        btnAjouterEx_session = findViewById(R.id.btnAjouterExercice_session);

        // Initialisation de la liste d'exercices de la session
        exercicesSession = new ArrayList<>();
        // Si un entraînement existant est fourni, pré-remplir la liste
        CycleEntrainement cycleChoisi = (CycleEntrainement) getIntent().getSerializableExtra("cycleChoisi");
        if (cycleChoisi != null) {
            exercicesSession.addAll(cycleChoisi.getExercices());
        }
        adapter = new ExerciceAdapter(exercicesSession);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Démarrer le chronomètre
        chronometre.setBase(SystemClock.elapsedRealtime());
        chronometre.start();  // le chronomètre tourne en continu jusqu'à l'arrêt manuel

        // Bouton "Terminer" pour arrêter la session
        btnTerminer.setOnClickListener(v -> {
            chronometre.stop();
            // Calculer le temps total écoulé
            long elapsedMillis = SystemClock.elapsedRealtime() - chronometre.getBase();
            int seconds = (int) (elapsedMillis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            String duree = String.format("%d min %d sec", minutes, seconds);
            Toast.makeText(this, "Entraînement terminé ! Durée : " + duree, Toast.LENGTH_LONG).show();
            finish();
        });

        // Ajouter un exercice en cours de session (surtout utile pour l'entraînement libre)
        btnAjouterEx_session.setOnClickListener(v -> ajouterExerciceEnSession());
    }

    private void ajouterExerciceEnSession() {
        // Récupérer les valeurs saisies pour le nouvel exercice
        String nom = inputNomEx_session.getText().toString().trim();
        String seriesStr = inputSeries_session.getText().toString().trim();
        String repsStr   = inputReps_session.getText().toString().trim();
        String poidsStr  = inputPoids_session.getText().toString().trim();
        String reposStr  = inputRepos_session.getText().toString().trim();

        // Vérifier les champs obligatoires
        if (TextUtils.isEmpty(nom) || TextUtils.isEmpty(seriesStr)
                || TextUtils.isEmpty(repsStr) || TextUtils.isEmpty(reposStr)) {
            Toast.makeText(this, "Veuillez remplir le nom, séries, répétitions et repos", Toast.LENGTH_SHORT).show();
            return;
        }
        int series = Integer.parseInt(seriesStr);
        int repetitions = Integer.parseInt(repsStr);
        int repos = Integer.parseInt(reposStr);
        int poids = 0;
        if (!TextUtils.isEmpty(poidsStr)) {
            poids = Integer.parseInt(poidsStr);
        }

        // Ajouter le nouvel exercice à la liste de la session
        Exercice ex = new Exercice(nom, series, repetitions, repos, poids);
        exercicesSession.add(ex);
        adapter.notifyItemInserted(exercicesSession.size() - 1);

        // Réinitialiser les champs de saisie
        inputNomEx_session.setText("");
        inputSeries_session.setText("");
        inputReps_session.setText("");
        inputPoids_session.setText("");
        inputRepos_session.setText("");
    }
}

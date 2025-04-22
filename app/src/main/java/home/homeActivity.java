package home;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import com.example.appsanst.EntrainementActivity;
import com.example.appsanst.R;
import com.example.appsanst.RepasActivity;
import com.example.appsanst.PoidsActivity; // Assurez-vous que cette activité existe.
import com.example.appsanst.ObjectifsActivity; // Assurez-vous que cette activité existe.

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Récupérer le nom d'utilisateur depuis l'Intent
        String displayName = getIntent().getStringExtra("displayName");

        // Afficher le message de bienvenue
        TextView welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setText(getString(R.string.welcome) + " " + displayName);

        // Configurer le bouton pour la page Repas
        Button buttonRepas = findViewById(R.id.button_repas);
        buttonRepas.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, RepasActivity.class);
            startActivity(intent);
        });

        // Configurer le bouton pour la page Entraînement
        Button buttonEntrainement = findViewById(R.id.button_entrainement);
        buttonEntrainement.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, EntrainementActivity.class);
            startActivity(intent);
        });

        // Configurer le bouton pour la page Poids
        Button buttonPoids = findViewById(R.id.button_poids);
        buttonPoids.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, PoidsActivity.class); // Assurez-vous que cette activité existe.
            startActivity(intent);
        });

        // Configurer le bouton pour la page Objectifs
        Button buttonObjectifs = findViewById(R.id.button_objectifs);
        buttonObjectifs.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, ObjectifsActivity.class); // Assurez-vous que cette activité existe.
            startActivity(intent);
        });
    }
}

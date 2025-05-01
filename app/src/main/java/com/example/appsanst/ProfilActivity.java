package com.example.appsanst;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // Récupérer les informations de l'utilisateur connecté
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String userId = prefs.getString("user_id", "");
        String displayName = prefs.getString("display_name", "Utilisateur");

        // Afficher les informations de l'utilisateur
        TextView textViewNom = findViewById(R.id.text_nom_utilisateur);
        TextView textViewId = findViewById(R.id.text_id_utilisateur);
        TextView textViewEmail = findViewById(R.id.text_email);

        textViewNom.setText(displayName);
        textViewId.setText("ID: " + userId);

        // Email (dans cet exemple, on utilise le même que l'ID)
        // Dans un cas réel, vous récupéreriez l'email depuis votre base de données
        textViewEmail.setText("test@test.com");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

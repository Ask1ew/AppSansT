package com.example.appsanst;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EntrainementActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activer Edge-to-Edge
        EdgeToEdge.enable(this);

        // Définir le layout associé à cette activité
        setContentView(R.layout.activity_entrainement);

        // Récupérer la vue principale avec l'ID "main"
        View mainView = findViewById(R.id.main);

        if (mainView != null) { // Vérifiez que la vue n'est pas null
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } else {
            throw new NullPointerException("La vue avec l'ID 'main' est introuvable.");
        }
    }
}

package com.example.appsanst;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import fragments.menuBarre;


public class RepasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repas);

        if (savedInstanceState == null) {
            // Créer une instance du fragment
            menuBarre fragment = new menuBarre();

            // Démarrer une transaction de fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.conteneurFragment, fragment)
                    .commit();
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_repas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
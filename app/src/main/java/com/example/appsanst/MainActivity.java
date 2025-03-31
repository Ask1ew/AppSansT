package com.example.appsanst;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Rediriger vers une autre activité si l'utilisateur est connecté
            Intent intent = new Intent(MainActivity.this, home.homeActivity.class);
            startActivity(intent);
        } else {
            // Rediriger vers LoginActivity si l'utilisateur n'est pas connecté
            Intent intent = new Intent(MainActivity.this, connexion.ui.login.LoginActivity.class);
            startActivity(intent);
        }

        finish();
    }

}

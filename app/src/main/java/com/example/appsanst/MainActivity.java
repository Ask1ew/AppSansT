package com.example.appsanst;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Redirection vers LoginActivity
        Intent intent = new Intent(MainActivity.this, connexion.ui.login.LoginActivity.class);
        startActivity(intent);

        // Terminer MainActivity pour éviter de revenir en arrière
        finish();
    }
}

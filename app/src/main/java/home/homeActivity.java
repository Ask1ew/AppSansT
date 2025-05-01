package home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appsanst.EntrainementActivity;
import com.example.appsanst.R;
import com.example.appsanst.RepasActivity;
import com.example.appsanst.PoidsActivity;
import com.example.appsanst.ObjectifsActivity;
import com.google.android.material.card.MaterialCardView;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Message de bienvenue personnalisé
        TextView welcomeText = findViewById(R.id.welcome_text);
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String displayName = prefs.getString("display_name", null);
        if (displayName != null && !displayName.isEmpty()) {
            welcomeText.setText(getString(R.string.welcome) + " " + displayName + " !");
        } else {
            welcomeText.setText(getString(R.string.welcome));
        }

        // Navigation par cartes
        MaterialCardView cardRepas = findViewById(R.id.card_repas);
        MaterialCardView cardEntrainement = findViewById(R.id.card_entrainement);
        MaterialCardView cardPoids = findViewById(R.id.card_poids);
        MaterialCardView cardObjectifs = findViewById(R.id.card_objectifs);

        cardRepas.setOnClickListener(v -> startActivity(new Intent(this, RepasActivity.class)));
        cardEntrainement.setOnClickListener(v -> startActivity(new Intent(this, EntrainementActivity.class)));
        cardPoids.setOnClickListener(v -> startActivity(new Intent(this, PoidsActivity.class)));
        cardObjectifs.setOnClickListener(v -> startActivity(new Intent(this, ObjectifsActivity.class)));
    }
}

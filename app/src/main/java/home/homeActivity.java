import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import com.example.appsanst.R;
import com.example.appsanst.RepasActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // Récupérer le nom d'utilisateur depuis l'Intent
        String displayName = getIntent().getStringExtra("displayName");

        // Afficher le message de bienvenue
        TextView welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setText(getString(R.string.welcome) + " " + displayName);

        // Configurer le bouton pour la page Repas
        Button buttonRepas = findViewById(R.id.button_repas);
        buttonRepas.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RepasActivity.class);
            startActivity(intent);
        });

        // Configurer le bouton pour la page Entraînement
        Button buttonEntrainement = findViewById(R.id.button_entrainement);
        buttonEntrainement.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EntrainementActivity.class); // Assurez-vous que cette activité existe.
            startActivity(intent);
        });
    }
}

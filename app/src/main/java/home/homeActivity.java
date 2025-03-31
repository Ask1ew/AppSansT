package home;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.appsanst.R;
import fragments.menuBarre;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // Ajouter dynamiquement le fragment menuBarre
        menuBarre menuFragment = new menuBarre();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_fragment_container, menuFragment)
                .commit();

        // Récupérer et afficher le nom d'utilisateur
        String displayName = getIntent().getStringExtra("displayName");
        TextView welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setText(getString(R.string.welcome) + " " + displayName);
    }

}

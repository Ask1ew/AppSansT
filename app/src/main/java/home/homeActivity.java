package home;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.appsanst.R;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // Récupérer le displayName depuis l'Intent
        String displayName = getIntent().getStringExtra("displayName");

        // Afficher le message de bienvenue avec le displayName
        TextView welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setText(getString(R.string.welcome) + " " + displayName);
    }
}

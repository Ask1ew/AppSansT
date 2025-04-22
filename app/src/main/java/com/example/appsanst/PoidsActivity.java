package com.example.appsanst;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.ressources.PoidsEntry;
import com.example.appsanst.ressources.PoidsAdapter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PoidsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poids);

        // Exemple d'historique de poids
        List<PoidsEntry> poidsEntries = Arrays.asList(
                new PoidsEntry(getDate(-3), 68.5f),
                new PoidsEntry(getDate(-2), 68.2f),
                new PoidsEntry(getDate(-1), 68.0f),
                new PoidsEntry(getDate(0), 67.8f)
        );

        RecyclerView recyclerView = findViewById(R.id.recyclerView_poids);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PoidsAdapter(poidsEntries));
    }

    // Renvoie une date à N jours du jour courant
    private Date getDate(int daysAgo) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, daysAgo);
        return cal.getTime();
    }
}

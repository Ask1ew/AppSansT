package com.example.appsanst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ObjectifsActivity extends AppCompatActivity {

    private TextInputEditText inCal, inProt, inLip, inGlu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectifs);

        inCal  = findViewById(R.id.inputObjCalories);
        inProt = findViewById(R.id.inputObjProteines);
        inLip  = findViewById(R.id.inputObjLipides);
        inGlu  = findViewById(R.id.inputObjGlucides);

        // pré‑remplissage
        Intent src = getIntent();
        inCal .setText(String.valueOf(src.getIntExtra("calories",  2000)));
        inProt.setText(String.valueOf(src.getIntExtra("proteines", 100)));
        inLip .setText(String.valueOf(src.getIntExtra("lipides",    70)));
        inGlu .setText(String.valueOf(src.getIntExtra("glucides",  250)));

        MaterialButton btnSave = findViewById(R.id.btnSaveObjectifs);
        btnSave.setOnClickListener(v -> save());
    }

    private void save() {
        if (TextUtils.isEmpty(inCal.getText()) || TextUtils.isEmpty(inProt.getText())
                || TextUtils.isEmpty(inLip.getText()) || TextUtils.isEmpty(inGlu.getText())) {
            Toast.makeText(this, "Remplis tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra("objectifCalories",  Integer.parseInt(inCal .getText().toString()));
        data.putExtra("objectifProteines", Integer.parseInt(inProt.getText().toString()));
        data.putExtra("objectifLipides",   Integer.parseInt(inLip .getText().toString()));
        data.putExtra("objectifGlucides",  Integer.parseInt(inGlu .getText().toString()));

        setResult(Activity.RESULT_OK, data);
        finish();
    }
}

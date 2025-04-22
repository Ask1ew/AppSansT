package com.example.appsanst.ressources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.R;

import java.util.List;

public class RepasAdapter extends RecyclerView.Adapter<RepasAdapter.RepasViewHolder> {

    private final List<Repas> data;

    public RepasAdapter(List<Repas> data) {
        this.data = data;
    }

    static class RepasViewHolder extends RecyclerView.ViewHolder {
        TextView nom, calories, macros;

        RepasViewHolder(@NonNull View v) {
            super(v);
            nom      = v.findViewById(R.id.textNomRepas);
            calories = v.findViewById(R.id.textCaloriesRepas);
            macros   = v.findViewById(R.id.textMacrosRepas);
        }

        void bind(Repas r) {
            nom.setText(r.getNom());

            calories.setText(r.getCalories() + " kcal");

            String m = "P " + r.getProteines() + " g   •   "
                    + "G " + r.getGlucides()  + " g   •   "
                    + "L " + r.getLipides()   + " g";
            macros.setText(m);
        }
    }

    @NonNull
    @Override
    public RepasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repas, parent, false);
        return new RepasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RepasViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() { return data.size(); }
}

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

    private List<Repas> listeDesRepas;

    public RepasAdapter(List<Repas> listeDesRepas) {
        this.listeDesRepas = listeDesRepas;
    }

    public static class RepasViewHolder extends RecyclerView.ViewHolder {
        TextView textNomRepas, textCalories, textMacros;

        public RepasViewHolder(@NonNull View itemView) {
            super(itemView);
            textNomRepas = itemView.findViewById(R.id.textNomRepas);
            textCalories = itemView.findViewById(R.id.textCalories);
            textMacros = itemView.findViewById(R.id.textMacros);
        }

        public void bind(Repas repas) {
            textNomRepas.setText(repas.getNom());
            textCalories.setText("Calories : " + repas.getCalories() + " kcal");
            textMacros.setText("Glucides : " + repas.getGlucides() + "g | Protéines : " + repas.getProteines() + "g | Lipides : " + repas.getLipides() + "g");
        }
    }

    @NonNull
    @Override
    public RepasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vue = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repas, parent, false);
        return new RepasViewHolder(vue);
    }

    @Override
    public void onBindViewHolder(@NonNull RepasViewHolder holder, int position) {
        holder.bind(listeDesRepas.get(position));
    }

    @Override
    public int getItemCount() {
        return listeDesRepas.size();
    }
}

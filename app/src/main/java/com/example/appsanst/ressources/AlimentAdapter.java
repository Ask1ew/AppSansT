package com.example.appsanst.ressources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.R;

import java.util.List;

public class AlimentAdapter extends RecyclerView.Adapter<AlimentAdapter.AlimentViewHolder> {

    private List<Aliment> aliments;

    public AlimentAdapter(List<Aliment> aliments) {
        this.aliments = aliments;
    }

    public static class AlimentViewHolder extends RecyclerView.ViewHolder {
        TextView textNom, textQuantite, textCalories;

        public AlimentViewHolder(@NonNull View itemView) {
            super(itemView);
            textNom = itemView.findViewById(R.id.textNom);
            textQuantite = itemView.findViewById(R.id.textQuantite);
            textCalories = itemView.findViewById(R.id.textCalories);
        }

        public void bind(Aliment aliment) {
            textNom.setText(aliment.getNom());
            textQuantite.setText(aliment.getQuantite());
            textCalories.setText(aliment.getCalories() + " kcal");
        }
    }

    @NonNull
    @Override
    public AlimentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vue = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aliment, parent, false);
        return new AlimentViewHolder(vue);
    }

    @Override
    public void onBindViewHolder(@NonNull AlimentViewHolder holder, int position) {
        holder.bind(aliments.get(position));
    }

    @Override
    public int getItemCount() {
        return aliments.size();
    }
}


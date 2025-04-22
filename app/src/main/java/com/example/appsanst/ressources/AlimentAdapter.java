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

    private final List<Aliment> aliments;

    public AlimentAdapter(List<Aliment> aliments) {
        this.aliments = aliments;
    }

    static class AlimentViewHolder extends RecyclerView.ViewHolder {
        TextView textNom, textQuantite, textCalories;

        AlimentViewHolder(@NonNull View itemView) {
            super(itemView);
            textNom      = itemView.findViewById(R.id.textNom);
            textQuantite = itemView.findViewById(R.id.textQuantite);
            textCalories = itemView.findViewById(R.id.textCalories);
        }

        void bind(Aliment aliment) {
            textNom.setText(aliment.getNom());
            textQuantite.setText(aliment.getQuantite());

            // Ex. : 123 kcal – P 10 g / G 20 g / L 5 g
            String infos = aliment.getCalories()   + " kcal  —  "
                    + "P " + aliment.getProteines() + " g / "
                    + "G " + aliment.getGlucides() + " g / "
                    + "L " + aliment.getLipides()  + " g";
            textCalories.setText(infos);
        }
    }

    @NonNull
    @Override
    public AlimentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_aliment, parent, false);
        return new AlimentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlimentViewHolder holder, int position) {
        holder.bind(aliments.get(position));
    }

    @Override
    public int getItemCount() { return aliments.size(); }
}

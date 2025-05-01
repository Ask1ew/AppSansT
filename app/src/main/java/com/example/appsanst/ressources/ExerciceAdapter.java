package com.example.appsanst.ressources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.R;
import java.util.List;

public class ExerciceAdapter extends RecyclerView.Adapter<ExerciceAdapter.ExerciceViewHolder> {
    private final List<Exercice> exercices;

    public ExerciceAdapter(List<Exercice> exercices) {
        this.exercices = exercices;
    }

    static class ExerciceViewHolder extends RecyclerView.ViewHolder {
        TextView textNom, textSeries, textRepetitions, textPoids, textRepos;
        ExerciceViewHolder(@NonNull View itemView) {
            super(itemView);
            textNom         = itemView.findViewById(R.id.textNomExercice);
            textSeries      = itemView.findViewById(R.id.textSeries);
            textRepetitions = itemView.findViewById(R.id.textRepetitions);
            textPoids       = itemView.findViewById(R.id.textPoids);
            textRepos       = itemView.findViewById(R.id.textRepos);
        }
    }

    @NonNull
    @Override
    public ExerciceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercice, parent, false);
        return new ExerciceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciceViewHolder holder, int position) {
        Exercice ex = exercices.get(position);
        holder.textNom.setText(ex.getNom());
        holder.textSeries.setText("Séries : " + ex.getSeries());
        holder.textRepetitions.setText("Répétitions : " + ex.getRepetitions());
        if (ex.getPoids() > 0) {
            holder.textPoids.setText("Poids : " + ex.getPoids() + " kg");
            holder.textPoids.setVisibility(View.VISIBLE);
        } else {
            // Masquer la ligne du poids si non renseigné
            holder.textPoids.setVisibility(View.GONE);
        }
        holder.textRepos.setText("Repos : " + ex.getTempsRepos() + " sec");
    }

    @Override
    public int getItemCount() {
        return exercices.size();
    }
}

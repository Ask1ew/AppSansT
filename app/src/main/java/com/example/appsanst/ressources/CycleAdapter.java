// CycleAdapter.java – Affichage du poids dans la liste des cycles (si > 0)
package com.example.appsanst.ressources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.R;
import java.util.List;

public class CycleAdapter extends RecyclerView.Adapter<CycleAdapter.CycleViewHolder> {
    private final List<CycleEntrainement> cycles;
    public CycleAdapter(List<CycleEntrainement> cycles) {
        this.cycles = cycles;
    }

    static class CycleViewHolder extends RecyclerView.ViewHolder {
        TextView title, exercices;
        CycleViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_cycle_title);
            exercices = itemView.findViewById(R.id.textView_exercices);
        }
    }

    @NonNull
    @Override
    public CycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cycle, parent, false);
        return new CycleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CycleViewHolder holder, int position) {
        CycleEntrainement cycle = cycles.get(position);
        holder.title.setText(cycle.getNom());
        // Construire la liste des exercices en texte, incluant le poids si présent
        StringBuilder sb = new StringBuilder();
        for (Exercice ex : cycle.getExercices()) {
            sb.append("• ").append(ex.getNom()).append(" : ")
                    .append(ex.getSeries()).append(" x ").append(ex.getRepetitions());
            if (ex.getPoids() > 0) {
                sb.append(" (Poids ").append(ex.getPoids()).append(" kg)");
            }
            sb.append(" – Repos ").append(ex.getTempsRepos()).append("s\n");
        }
        holder.exercices.setText(sb.toString().trim());
    }

    @Override
    public int getItemCount() {
        return cycles.size();
    }
}

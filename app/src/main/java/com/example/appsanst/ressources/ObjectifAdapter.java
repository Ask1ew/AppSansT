package com.example.appsanst.ressources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.R;
import com.example.appsanst.ressources.Objectif;
import java.util.List;

public class ObjectifAdapter extends RecyclerView.Adapter<ObjectifAdapter.ObjectifViewHolder> {

    private final List<Objectif> objectifs;

    public ObjectifAdapter(List<Objectif> objectifs) {
        this.objectifs = objectifs;
    }

    @NonNull
    @Override
    public ObjectifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_objectif, parent, false);
        return new ObjectifViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectifViewHolder holder, int position) {
        Objectif obj = objectifs.get(position);

        holder.titre.setText(obj.getTitre());
        holder.description.setText(obj.getDescription());
        holder.progressBar.setMax(obj.getCible());
        holder.progressBar.setProgress(obj.getProgres());
        holder.progression.setText(obj.getProgres() + " / " + obj.getCible());
    }

    @Override
    public int getItemCount() {
        return objectifs.size();
    }

    static class ObjectifViewHolder extends RecyclerView.ViewHolder {
        TextView titre, description, progression;
        ProgressBar progressBar;

        ObjectifViewHolder(View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.textView_titre_objectif);
            description = itemView.findViewById(R.id.textView_description_objectif);
            progressBar = itemView.findViewById(R.id.progressBar);
            progression = itemView.findViewById(R.id.textView_progression_objectif);
        }
    }
}

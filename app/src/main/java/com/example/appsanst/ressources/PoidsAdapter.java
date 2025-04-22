package com.example.appsanst.ressources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appsanst.R;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PoidsAdapter extends RecyclerView.Adapter<PoidsAdapter.PoidsViewHolder> {

    private final List<PoidsEntry> poidsEntries;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public PoidsAdapter(List<PoidsEntry> poidsEntries) {
        this.poidsEntries = poidsEntries;
    }

    @NonNull
    @Override
    public PoidsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poids, parent, false);
        return new PoidsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PoidsViewHolder holder, int position) {
        PoidsEntry entry = poidsEntries.get(position);
        holder.date.setText(dateFormat.format(entry.getDate()));
        holder.poids.setText(String.format(Locale.getDefault(), "%.1f kg", entry.getPoids()));
    }

    @Override
    public int getItemCount() {
        return poidsEntries.size();
    }

    static class PoidsViewHolder extends RecyclerView.ViewHolder {
        TextView date, poids;

        PoidsViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textView_date);
            poids = itemView.findViewById(R.id.textView_poids);
        }
    }
}

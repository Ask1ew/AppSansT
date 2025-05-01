package fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appsanst.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddWeightFragment extends Fragment {

    // Interface pour communiquer avec l'activité
    public interface OnAddWeightListener {
        void onAddWeight();
    }

    private OnAddWeightListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAddWeightListener) {
            listener = (OnAddWeightListener) context;
        } else {
            throw new RuntimeException(context.toString() + " doit implémenter OnAddWeightListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_weight, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddWeight();
            }
        });

        return view;
    }
}

package com.example.appsanst.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.appsanst.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment affichant un FAB réutilisable.
 * L'action déclenchée est passée via setArguments("action", …) ou par le host qui implémente l'interface.
 */
public class AddFabFragment extends Fragment {

    private static final String ARG_ACTION = "action";
    public  static final String ACTION_ADD_MEAL   = "addMeal";
    public  static final String ACTION_ADD_WEIGHT = "addWeight";

    /** callback facultative vers l'activité */
    public interface OnFabAction {
        void onFabAction(String actionTag);
    }

    private OnFabAction callback;
    private String      action;

    public static AddFabFragment newInstance(String action) {
        AddFabFragment f = new AddFabFragment();
        Bundle b = new Bundle();
        b.putString(ARG_ACTION, action);
        f.setArguments(b);
        return f;
    }

    @Override public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFabAction) callback = (OnFabAction) context;
    }
    @Override public void onCreate(@Nullable Bundle saved) {
        super.onCreate(saved);
        action = (getArguments() != null) ? getArguments().getString(ARG_ACTION) : ACTION_ADD_MEAL;
    }
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inf, @Nullable ViewGroup c, @Nullable Bundle s) {
        return inf.inflate(R.layout.fragment_add_fab, c, false);
    }
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        FloatingActionButton fab = v.findViewById(R.id.fabAdd);
        /* icône différente selon l'action */
        int icon = ACTION_ADD_WEIGHT.equals(action)
                ? android.R.drawable.ic_menu_edit   // icône système disponible
                : android.R.drawable.ic_menu_add;
        fab.setImageResource(icon);


        fab.setOnClickListener(btn -> {
            if (callback != null) callback.onFabAction(action);
        });
    }
}

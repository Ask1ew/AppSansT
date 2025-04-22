package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.appsanst.R;

public class menuBarre extends Fragment {

    public menuBarre() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_barre, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Clic sur le texte central : retour à LoginActivity
        view.findViewById(R.id.textViewMenuBarre).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), home.homeActivity.class));
        });

        // Clic sur le bouton burger : menu déroulant
        view.findViewById(R.id.buttonBurger).setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_accueil, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_accueil) {
                    startActivity(new Intent(getActivity(), home.homeActivity.class));
                    return true;
                } else if (itemId == R.id.menu_repas) {
                    startActivity(new Intent(getActivity(), com.example.appsanst.RepasActivity.class));
                    return true;
                } else if (itemId == R.id.menu_entrainement) {
                    startActivity(new Intent(getActivity(), com.example.appsanst.EntrainementActivity.class));
                    return true;
                } else if (itemId == R.id.menu_poids) {
                    startActivity(new Intent(getActivity(), com.example.appsanst.PoidsActivity.class));
                    return true;
                } else if (itemId == R.id.menu_objectifs) {
                startActivity(new Intent(getActivity(), com.example.appsanst.ObjectifsActivity.class));
                return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }

}
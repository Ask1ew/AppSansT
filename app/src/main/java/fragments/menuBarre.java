package fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.appsanst.R;
import connexion.data.LoginDataSource;
import connexion.data.LoginRepository;
import connexion.login.LoginActivity;

public class menuBarre extends Fragment {

    public menuBarre() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_barre, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.textViewMenuBarre).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), home.homeActivity.class));
        });

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
                } else if (itemId == R.id.menu_logout) {
                    // Suppression des préférences de session
                    requireActivity().getSharedPreferences("user_session", 0).edit().clear().apply();

                    // Utiliser requireContext() pour obtenir le contexte du fragment
                    Context appContext = requireActivity().getApplicationContext();
                    LoginRepository.getInstance(new LoginDataSource(appContext), appContext).logout();

                    Toast.makeText(requireContext(), "Déconnexion réussie", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), connexion.login.LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }
}
package fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.appsanst.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
            navigateTo(home.homeActivity.class);
        });

        view.findViewById(R.id.buttonBurger).setOnClickListener(v -> {
            showBottomSheetMenu();
        });

        view.findViewById(R.id.buttonCompteMenuBarre).setOnClickListener(v -> {
            // Afficher le profil utilisateur ou les paramètres du compte
            Toast.makeText(requireContext(), "Profil utilisateur", Toast.LENGTH_SHORT).show();
        });
    }

    private void showBottomSheetMenu() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(R.layout.bottom_sheet_menu, null);

        // Configuration des éléments du menu
        bottomSheetView.findViewById(R.id.menu_item_accueil).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            navigateTo(home.homeActivity.class);
        });

        bottomSheetView.findViewById(R.id.menu_item_repas).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            navigateTo(com.example.appsanst.RepasActivity.class);
        });

        bottomSheetView.findViewById(R.id.menu_item_entrainement).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            navigateTo(com.example.appsanst.EntrainementActivity.class);
        });

        bottomSheetView.findViewById(R.id.menu_item_poids).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            navigateTo(com.example.appsanst.PoidsActivity.class);
        });

        bottomSheetView.findViewById(R.id.menu_item_objectifs).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            navigateTo(com.example.appsanst.ObjectifsActivity.class);
        });

        bottomSheetView.findViewById(R.id.menu_item_logout).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            deconnexion();
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void navigateTo(Class<?> destination) {
        startActivity(new Intent(getActivity(), destination));
    }

    private void deconnexion() {
        // Suppression des préférences de session
        requireActivity().getSharedPreferences("user_session", 0).edit().clear().apply();

        // Déconnexion via Repository
        Context appContext = requireActivity().getApplicationContext();
        LoginRepository.getInstance(new LoginDataSource(appContext), appContext).logout();

        Toast.makeText(requireContext(), "Déconnexion réussie", Toast.LENGTH_SHORT).show();

        // Redirection vers l'écran de connexion
        Intent intent = new Intent(getActivity(), connexion.login.LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}

package connexion.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.appsanst.R;
import com.example.appsanst.databinding.ActivityLoginBinding;

import home.homeActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private android.widget.ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Utiliser le factory avec contexte
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(this))
                .get(LoginViewModel.class);

        loadingProgressBar = binding.loading;

        final android.widget.EditText usernameEditText = binding.username;
        final android.widget.EditText passwordEditText = binding.password;
        final android.widget.Button loginButton = binding.login;

        // Vérifier si l'utilisateur est déjà connecté
        if (loginViewModel.isUserLoggedIn()) {
            startActivity(new Intent(this, homeActivity.class));
            finish();
            return;
        }

        // Observer l'état du formulaire
        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) return;

            loginButton.setEnabled(loginFormState.isDataValid());

            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        // Observer le résultat du login
        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) return;

            loadingProgressBar.setVisibility(View.GONE);

            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }

            setResult(Activity.RESULT_OK);
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString()
                );
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                triggerLogin();
            }
            return false;
        });

        loginButton.setOnClickListener(v -> triggerLogin());
    }

    private void triggerLogin() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        loginViewModel.login(
                binding.username.getText().toString(),
                binding.password.getText().toString()
        );
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + " " + model.getDisplayName();
        Toast.makeText(this, welcome, Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, homeActivity.class));
        finish();
    }

    private void showLoginFailed(@Nullable String errorMessage) {
        Toast.makeText(
                getApplicationContext(),
                errorMessage != null ? errorMessage : getString(R.string.login_failed),
                Toast.LENGTH_LONG
        ).show();
    }
}

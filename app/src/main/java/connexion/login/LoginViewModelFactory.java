package connexion.login;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import connexion.data.LoginDataSource;
import connexion.data.LoginRepository;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public LoginViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(
                    LoginRepository.getInstance(new LoginDataSource(context), context)
            );
        }
        throw new IllegalArgumentException("Classe ViewModel inconnue");
    }
}

package connexion.login;

import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.appsanst.R;
import connexion.data.LoginRepository;
import connexion.data.Result;
import connexion.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final LoginRepository loginRepository;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public boolean isUserLoggedIn() {
        return loginRepository.isLoggedIn();
    }

    public void login(String username, String password) {
        executor.execute(() -> {
            Result<LoggedInUser> result = loginRepository.login(username, password);
            if (result instanceof Result.Success) {
                LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                loginResult.postValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            } else {
                String error = ((Result.Error) result).getError().getMessage();
                loginResult.postValue(new LoginResult(error));
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isUserNameValid(String username) {
        return username != null && (username.contains("@")
                ? Patterns.EMAIL_ADDRESS.matcher(username).matches()
                : !username.trim().isEmpty());
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}

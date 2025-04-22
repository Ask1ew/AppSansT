package connexion.login;

import androidx.annotation.Nullable;

public class LoginResult {
    @Nullable
    private final LoggedInUserView success;
    @Nullable
    private final String error;

    public LoginResult(@Nullable String error) {
        this.error = error;
        this.success = null;
    }

    public LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
        this.error = null;
    }

    @Nullable
    public LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    public String getError() {
        return error;
    }
}

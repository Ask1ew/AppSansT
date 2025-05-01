package connexion.data;

import android.content.Context;
import connexion.data.model.LoggedInUser;
import database.DatabaseHelper;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private DatabaseHelper dbHelper;
    private Context context;

    public LoginDataSource(Context context) {
        this.context = context;
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public Result<LoggedInUser> login(String username, String password) {
        try {
            LoggedInUser user = dbHelper.checkUser(username, password);

            if (user != null) {
                return new Result.Success<>(user);
            } else {
                return new Result.Error(new IOException("Identifiants incorrects"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Erreur lors de la connexion", e));
        }
    }

    public void logout() {

    }
}

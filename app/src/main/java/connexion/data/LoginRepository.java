package connexion.data;

import android.content.Context;
import android.content.SharedPreferences;
import connexion.data.model.LoggedInUser;

/**
 * Classe qui gère l'authentification et conserve les informations utilisateur.
 */
public class LoginRepository {
    private static final String PREF_NAME = "user_session";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_DISPLAY_NAME = "display_name";

    private static volatile LoginRepository instance;
    private LoginDataSource dataSource;
    private SharedPreferences prefs;

    private LoggedInUser user = null;

    // Constructeur privé : accès singleton
    private LoginRepository(LoginDataSource dataSource, Context context) {
        this.dataSource = dataSource;
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Vérifier si une session existe
        String userId = prefs.getString(KEY_USER_ID, null);
        String displayName = prefs.getString(KEY_DISPLAY_NAME, null);

        if (userId != null && displayName != null) {
            this.user = new LoggedInUser(userId, displayName);
        }
    }

    public static LoginRepository getInstance(LoginDataSource dataSource, Context context) {
        if (instance == null) {
            instance = new LoginRepository(dataSource, context);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();

        // Supprimer la session
        prefs.edit().clear().apply();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;

        // Sauvegarder dans les SharedPreferences
        prefs.edit()
                .putString(KEY_USER_ID, user.getUserId())
                .putString(KEY_DISPLAY_NAME, user.getDisplayName())
                .apply();
    }

    public Result<LoggedInUser> login(String username, String password) {
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public LoggedInUser getLoggedInUser() {
        return user;
    }
}

package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appsanst.ressources.PoidsEntry;
import connexion.data.model.LoggedInUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "appsante.db";
    private static final int DATABASE_VERSION = 1;

    // Table Utilisateurs
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_DISPLAY_NAME = "display_name";

    // Table Poids
    private static final String TABLE_POIDS = "poids";
    private static final String COLUMN_POIDS_ID = "poids_id";
    private static final String COLUMN_POIDS_USER_ID = "user_id";
    private static final String COLUMN_POIDS_DATE = "date";
    private static final String COLUMN_POIDS_VALEUR = "valeur";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    // Singleton
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table utilisateurs
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " TEXT PRIMARY KEY,"
                + COLUMN_USERNAME + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_DISPLAY_NAME + " TEXT"
                + ")";

        // Création de la table poids
        String CREATE_POIDS_TABLE = "CREATE TABLE " + TABLE_POIDS + "("
                + COLUMN_POIDS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_POIDS_USER_ID + " TEXT,"
                + COLUMN_POIDS_DATE + " TEXT,"
                + COLUMN_POIDS_VALEUR + " REAL,"
                + "FOREIGN KEY(" + COLUMN_POIDS_USER_ID + ") REFERENCES "
                + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_POIDS_TABLE);

        // Ajouter un utilisateur de test
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, UUID.randomUUID().toString());
        values.put(COLUMN_USERNAME, "test@test.com");
        values.put(COLUMN_PASSWORD, "password");
        values.put(COLUMN_DISPLAY_NAME, "Utilisateur Test");
        db.insert(TABLE_USERS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POIDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // ------ Méthodes pour les utilisateurs ------

    public long addUser(String userId, String username, String password, String displayName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_DISPLAY_NAME, displayName);

        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public LoggedInUser checkUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {COLUMN_USER_ID, COLUMN_DISPLAY_NAME};
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        LoggedInUser user = null;

        if (cursor.moveToFirst()) {
            String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
            String displayName = cursor.getString(cursor.getColumnIndex(COLUMN_DISPLAY_NAME));
            user = new LoggedInUser(userId, displayName);
        }

        cursor.close();
        db.close();
        return user;
    }

    // ------ Méthodes pour les poids ------

    public long addPoidsEntry(String userId, Date date, float poids) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_POIDS_USER_ID, userId);
        values.put(COLUMN_POIDS_DATE, dateFormat.format(date));
        values.put(COLUMN_POIDS_VALEUR, poids);

        long id = db.insert(TABLE_POIDS, null, values);
        db.close();
        return id;
    }

    public List<PoidsEntry> getAllPoidsEntries(String userId) {
        List<PoidsEntry> entries = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_POIDS
                + " WHERE " + COLUMN_POIDS_USER_ID + " = ?"
                + " ORDER BY " + COLUMN_POIDS_DATE + " DESC";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userId});

        if (cursor.moveToFirst()) {
            do {
                try {
                    Date date = dateFormat.parse(cursor.getString(cursor.getColumnIndex(COLUMN_POIDS_DATE)));
                    float valeur = cursor.getFloat(cursor.getColumnIndex(COLUMN_POIDS_VALEUR));
                    entries.add(new PoidsEntry(date, valeur));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return entries;
    }
}

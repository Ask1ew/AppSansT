package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appsanst.ressources.PoidsEntry;
import com.example.appsanst.ressources.Repas;
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
    private static final int DATABASE_VERSION = 2;

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

    // Tables Repas et Objectifs
    private static final String TABLE_REPAS = "repas";
    private static final String TABLE_OBJECTIFS_NUTRITIONNELS = "objectifs_nutritionnels";

    // Colonnes repas
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOM_REPAS = "nom";
    private static final String COLUMN_CALORIES = "calories";
    private static final String COLUMN_PROTEINES = "proteines";
    private static final String COLUMN_LIPIDES = "lipides";
    private static final String COLUMN_GLUCIDES = "glucides";
    private static final String COLUMN_DATE = "date";

    // Colonnes objectifs
    private static final String COLUMN_OBJECTIF_CALORIES = "obj_calories";
    private static final String COLUMN_OBJECTIF_PROTEINES = "obj_proteines";
    private static final String COLUMN_OBJECTIF_LIPIDES = "obj_lipides";
    private static final String COLUMN_OBJECTIF_GLUCIDES = "obj_glucides";

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

        // Création de la table repas
        String CREATE_REPAS_TABLE = "CREATE TABLE " + TABLE_REPAS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID + " TEXT NOT NULL,"
                + COLUMN_NOM_REPAS + " TEXT NOT NULL,"
                + COLUMN_CALORIES + " INTEGER,"
                + COLUMN_PROTEINES + " INTEGER,"
                + COLUMN_LIPIDES + " INTEGER,"
                + COLUMN_GLUCIDES + " INTEGER,"
                + COLUMN_DATE + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";

        // Création de la table objectifs nutritionnels
        String CREATE_OBJECTIFS_TABLE = "CREATE TABLE " + TABLE_OBJECTIFS_NUTRITIONNELS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID + " TEXT UNIQUE,"
                + COLUMN_OBJECTIF_CALORIES + " INTEGER DEFAULT 2000,"
                + COLUMN_OBJECTIF_PROTEINES + " INTEGER DEFAULT 100,"
                + COLUMN_OBJECTIF_LIPIDES + " INTEGER DEFAULT 70,"
                + COLUMN_OBJECTIF_GLUCIDES + " INTEGER DEFAULT 250,"
                + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_POIDS_TABLE);
        db.execSQL(CREATE_REPAS_TABLE);
        db.execSQL(CREATE_OBJECTIFS_TABLE);

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBJECTIFS_NUTRITIONNELS);
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

    // --- MÉTHODES POUR REPAS ---

    public long ajouterRepas(String userId, Repas repas) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_NOM_REPAS, repas.getNom());
        values.put(COLUMN_CALORIES, repas.getCalories());
        values.put(COLUMN_PROTEINES, repas.getProteines());
        values.put(COLUMN_LIPIDES, repas.getLipides());
        values.put(COLUMN_GLUCIDES, repas.getGlucides());
        values.put(COLUMN_DATE, repas.getDate());

        long id = db.insert(TABLE_REPAS, null, values);
        db.close();
        return id;
    }

    public List<Repas> getRepasUtilisateur(String userId) {
        List<Repas> listeRepas = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_REPAS +
                " WHERE " + COLUMN_USER_ID + " = ?" +
                " ORDER BY " + COLUMN_DATE + " DESC";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{userId});

        if (cursor.moveToFirst()) {
            do {
                String nom = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_REPAS));
                int calories = cursor.getInt(cursor.getColumnIndex(COLUMN_CALORIES));
                int glucides = cursor.getInt(cursor.getColumnIndex(COLUMN_GLUCIDES));
                int proteines = cursor.getInt(cursor.getColumnIndex(COLUMN_PROTEINES));
                int lipides = cursor.getInt(cursor.getColumnIndex(COLUMN_LIPIDES));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

                Repas repas = new Repas(nom, calories, glucides, proteines, lipides);
                repas.setDate(date);

                listeRepas.add(repas);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listeRepas;
    }

    // --- MÉTHODES POUR OBJECTIFS ---

    public void sauvegarderObjectifs(String userId, int calories, int proteines, int lipides, int glucides) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_OBJECTIF_CALORIES, calories);
        values.put(COLUMN_OBJECTIF_PROTEINES, proteines);
        values.put(COLUMN_OBJECTIF_LIPIDES, lipides);
        values.put(COLUMN_OBJECTIF_GLUCIDES, glucides);

        // Mise à jour ou insertion (REPLACE)
        db.replace(TABLE_OBJECTIFS_NUTRITIONNELS, null, values);
        db.close();
    }

    public int[] getObjectifsNutritionnels(String userId) {
        int[] objectifs = new int[]{2000, 100, 70, 250}; // Valeurs par défaut

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_OBJECTIFS_NUTRITIONNELS,
                new String[]{
                        COLUMN_OBJECTIF_CALORIES,
                        COLUMN_OBJECTIF_PROTEINES,
                        COLUMN_OBJECTIF_LIPIDES,
                        COLUMN_OBJECTIF_GLUCIDES
                },
                COLUMN_USER_ID + "=?",
                new String[]{userId},
                null, null, null);

        if (cursor.moveToFirst()) {
            objectifs[0] = cursor.getInt(cursor.getColumnIndex(COLUMN_OBJECTIF_CALORIES));
            objectifs[1] = cursor.getInt(cursor.getColumnIndex(COLUMN_OBJECTIF_PROTEINES));
            objectifs[2] = cursor.getInt(cursor.getColumnIndex(COLUMN_OBJECTIF_LIPIDES));
            objectifs[3] = cursor.getInt(cursor.getColumnIndex(COLUMN_OBJECTIF_GLUCIDES));
        }

        cursor.close();
        db.close();
        return objectifs;
    }
}

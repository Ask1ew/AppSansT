package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appsanst.ressources.PoidsEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PoidsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "poids.db";
    private static final int DATABASE_VERSION = 1;

    // Table Poids
    private static final String TABLE_POIDS = "poids";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_VALEUR = "valeur";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public PoidsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POIDS_TABLE = "CREATE TABLE " + TABLE_POIDS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_VALEUR + " REAL"
                + ")";
        db.execSQL(CREATE_POIDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POIDS);
        onCreate(db);
    }

    public long ajouterPoids(String userId, Date date, float poids) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_DATE, dateFormat.format(date));
        values.put(COLUMN_VALEUR, poids);

        long id = db.insert(TABLE_POIDS, null, values);
        db.close();
        return id;
    }

    public List<PoidsEntry> getPoidsEntries(String userId) {
        List<PoidsEntry> entries = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_POIDS,
                new String[] {COLUMN_DATE, COLUMN_VALEUR},
                COLUMN_USER_ID + "=?",
                new String[] {userId},
                null, null,
                COLUMN_DATE + " DESC"
        );

        if (cursor.moveToFirst()) {
            do {
                try {
                    String dateStr = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                    float valeur = cursor.getFloat(cursor.getColumnIndex(COLUMN_VALEUR));
                    Date date = dateFormat.parse(dateStr);

                    PoidsEntry entry = new PoidsEntry(date, valeur);
                    entries.add(entry);
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

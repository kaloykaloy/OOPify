package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TUP_ID = "tup_id";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_MIDDLE_NAME = "middle_name";
    private static final String COLUMN_SUFFIX_NAME = "suffix_name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_BIRTHDATE = "birthdate";
    private static final String COLUMN_PROGRAM = "program";
    private static final String COLUMN_SPECIALIZATION = "specialization";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_PROFILE_IMAGE = "profile_image";

    private static final String TABLE_LEVEL_SYSTEM = "level_system";
    private static final String COLUMN_LEVEL_TUP_ID = "tup_id";
    private static final String COLUMN_LEVEL = "lvl";
    private static final String COLUMN_STAR = "star";
    private static final String COLUMN_POINTS = "points";
    private static final String COLUMN_BAR = "bar";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TUP_ID + " TEXT,"
                + COLUMN_LAST_NAME + " TEXT,"
                + COLUMN_FIRST_NAME + " TEXT,"
                + COLUMN_MIDDLE_NAME + " TEXT,"
                + COLUMN_SUFFIX_NAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_BIRTHDATE + " TEXT,"
                + COLUMN_PROGRAM + " TEXT,"
                + COLUMN_SPECIALIZATION + " TEXT,"
                + COLUMN_GENDER + " TEXT,"
                + COLUMN_PROFILE_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_LEVEL_SYSTEM_TABLE = "CREATE TABLE " + TABLE_LEVEL_SYSTEM + "("
                + COLUMN_LEVEL_TUP_ID + " TEXT PRIMARY KEY,"
                + COLUMN_LEVEL + " TEXT,"
                + COLUMN_STAR + " TEXT,"
                + COLUMN_POINTS + " TEXT,"
                + COLUMN_BAR + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_LEVEL_TUP_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_TUP_ID + ")"
                + ")";
        db.execSQL(CREATE_LEVEL_SYSTEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVEL_SYSTEM);
        onCreate(db);
    }

    public void addUser(String tupId, String lastName, String firstName, String middleName, String suffixName, String password, String email, String birthdate, String program, String specialization, String gender, String profileImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TUP_ID, tupId);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_MIDDLE_NAME, middleName);
        values.put(COLUMN_SUFFIX_NAME, suffixName);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_BIRTHDATE, birthdate);
        values.put(COLUMN_PROGRAM, program);
        values.put(COLUMN_SPECIALIZATION, specialization);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_PROFILE_IMAGE, profileImage);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void addLevelSystemData(String tupId, String lvl, String star, String points, String bar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LEVEL_TUP_ID, tupId);
        values.put(COLUMN_LEVEL, lvl);
        values.put(COLUMN_STAR, star);
        values.put(COLUMN_POINTS, points);
        values.put(COLUMN_BAR, bar);

        db.insert(TABLE_LEVEL_SYSTEM, null, values);
        db.close();
    }

    public boolean checkUser(String idNo, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_TUP_ID + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{idNo, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public Cursor getLevelSystemData(String tupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_LEVEL_SYSTEM + " WHERE " + COLUMN_LEVEL_TUP_ID + " = ?";
        return db.rawQuery(query, new String[]{tupId});
    }

    public boolean isTupIdExists(String tupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_TUP_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tupId});

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();

        return exists;
    }

    public Cursor getUserData(String tupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE tup_id = ?";
        return db.rawQuery(query, new String[]{tupId});
    }
    public void updateLevelSystemData(String tupId, String level, String star, String points, String bar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_LEVEL, level);
        values.put(COLUMN_STAR, star);
        values.put(COLUMN_POINTS, points);
        values.put(COLUMN_BAR, bar);

        db.update(TABLE_LEVEL_SYSTEM, values, COLUMN_LEVEL_TUP_ID + " = ?", new String[]{tupId});
        db.close();
    }

    public void updateUser(String tupId, String lastName, String firstName, String middleName, String suffixName, String password, String email, String birthdate, String program, String specialization, String gender, String profileImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_MIDDLE_NAME, middleName);
        values.put(COLUMN_SUFFIX_NAME, suffixName);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_BIRTHDATE, birthdate);
        values.put(COLUMN_PROGRAM, program);
        values.put(COLUMN_SPECIALIZATION, specialization);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_PROFILE_IMAGE, profileImage);

        db.update(TABLE_USERS, values, COLUMN_TUP_ID + " = ?", new String[]{tupId});
        db.close();
    }

    public Cursor getRankingData() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT users.first_name, level_system.lvl, level_system.points, level_system.star " +
                "FROM " + TABLE_USERS + " AS users " +
                "INNER JOIN " + TABLE_LEVEL_SYSTEM + " AS level_system " +
                "ON users.tup_id = level_system.tup_id " +
                "ORDER BY CAST(level_system.points AS INTEGER) DESC";

        return db.rawQuery(query, null);
    }

    public Cursor getUserPointsAndRank(String tupId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT users.tup_id, users.first_name, level_system.points, " +
                "(SELECT COUNT(*) + 1 FROM " + TABLE_LEVEL_SYSTEM + " AS ls WHERE CAST(ls.points AS INTEGER) > CAST(level_system.points AS INTEGER)) AS rank " +
                "FROM " + TABLE_USERS + " AS users " +
                "INNER JOIN " + TABLE_LEVEL_SYSTEM + " AS level_system " +
                "ON users.tup_id = level_system.tup_id " +
                "WHERE users.tup_id = ?";

        return db.rawQuery(query, new String[]{tupId});
    }

    public void addPoints(String tupId, int pointsToAdd) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COLUMN_POINTS + " FROM " + TABLE_LEVEL_SYSTEM + " WHERE " + COLUMN_LEVEL_TUP_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tupId});

        if (cursor != null && cursor.moveToFirst()) {
            String currentPointsStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POINTS));

            int currentPoints = Integer.parseInt(currentPointsStr);

            int updatedPoints = currentPoints + pointsToAdd;

            String updatedPointsStr = String.valueOf(updatedPoints);

            ContentValues values = new ContentValues();
            values.put(COLUMN_POINTS, updatedPointsStr);

            db.update(TABLE_LEVEL_SYSTEM, values, COLUMN_LEVEL_TUP_ID + " = ?", new String[]{tupId});
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
    }

}


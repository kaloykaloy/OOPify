package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String USER_SESSION = "UserSession";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String TUP_ID = "tupId";
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(USER_SESSION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }


    public String getTupId() {
        return sharedPreferences.getString(TUP_ID, "");
    }


    public void createLoginSession(String tupId) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(TUP_ID, tupId);
        editor.apply();
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}


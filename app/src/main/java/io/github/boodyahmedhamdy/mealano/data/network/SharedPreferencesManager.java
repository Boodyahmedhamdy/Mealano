package io.github.boodyahmedhamdy.mealano.data.network;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String IS_FIRST_TIME_FLAG = "IS_FIRST_TIME_FLAG";
    private static final boolean IS_FIRST_TIME_FLAG_DEFAULT_VALUE = true;
    private static final String USER_ID_FLAG = "USER_ID_FLAG";
    private static final String USER_ID_FLAG_DEFAULT_VALUE = null;
    private static SharedPreferencesManager instance;

    private static final String MEALANO_SHARED_PREF = "MEALANO_SHARED_PREF";
    private static final String IS_LOGGED_IN_FLAG = "IS_LOGGED_IN_FLAG";
    private static final boolean IS_LOGGED_IN_FLAG_DEFAULT_VALUE = false;
    private final SharedPreferences sharedPreferences;

    private SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(MEALANO_SHARED_PREF, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN_FLAG, IS_LOGGED_IN_FLAG_DEFAULT_VALUE);
    }

    public boolean isFirstTime() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_FLAG, IS_FIRST_TIME_FLAG_DEFAULT_VALUE);
    }

    public void updateIsFirstTime(boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_TIME_FLAG, value);
        editor.apply();
    }

    public void updateUserId(String uid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID_FLAG, uid);
        editor.apply();
    }

    public String getUserId() {
        return  sharedPreferences.getString(USER_ID_FLAG, USER_ID_FLAG_DEFAULT_VALUE);
    }
}

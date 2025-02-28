package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String IS_FIRST_TIME_FLAG = "IS_FIRST_TIME_FLAG";
    private static final boolean IS_FIRST_TIME_FLAG_DEFAULT_VALUE = true;

    private static final String MEALANO_SHARED_PREF = "MEALANO_SHARED_PREF";
    private final SharedPreferences sharedPreferences;
    private static SharedPreferencesManager instance;

    private SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(MEALANO_SHARED_PREF, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    public boolean isFirstTime() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_FLAG, IS_FIRST_TIME_FLAG_DEFAULT_VALUE);
    }

    public void setIsFirstTime(boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_TIME_FLAG, value);
        editor.apply();
    }

}

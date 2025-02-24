package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.internal.IdTokenListener;
import com.google.firebase.internal.InternalTokenResult;

import io.github.boodyahmedhamdy.mealano.profile.contract.OnSignOutCallback;

public class UsersLocalDataSource {
    private static final String TAG = "UsersLocalDataSource";
    SharedPreferencesManager sharedPreferencesManager;
    FirebaseAuth firebaseAuth;

    private UsersLocalDataSource(SharedPreferencesManager sharedPreferencesManager, FirebaseAuth firebaseAuth) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.firebaseAuth = firebaseAuth;
    }

    private static UsersLocalDataSource instance;

    public static UsersLocalDataSource getInstance(SharedPreferencesManager sharedPreferencesManager, FirebaseAuth firebaseAuth) {
        if(instance == null) {
            instance = new UsersLocalDataSource(sharedPreferencesManager, firebaseAuth);
        }
        return instance;
    }

    public Boolean isFirstTime() {
        return sharedPreferencesManager.isFirstTime();
    }

    public Boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public void signOut(OnSignOutCallback callback) {
        Log.i(TAG, "signOut: started");
        try {
            firebaseAuth.signOut();
            callback.onSuccess();
        } catch (Exception e) {
            callback.onFailure(e.getMessage());
        }
        Log.i(TAG, "signOut: finished");
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
}

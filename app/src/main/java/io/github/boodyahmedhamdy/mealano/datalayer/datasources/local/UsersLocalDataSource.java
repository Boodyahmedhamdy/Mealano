package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.boodyahmedhamdy.mealano.profile.contract.OnSignOutCallback;

public class UsersLocalDataSource {
    private static final String TAG = "UsersLocalDataSource";
    SharedPreferencesManager sharedPreferencesManager;
    FirebaseAuth firebaseAuth;

    public UsersLocalDataSource(SharedPreferencesManager sharedPreferencesManager, FirebaseAuth firebaseAuth) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.firebaseAuth = firebaseAuth;
    }

    public Boolean isFirstTime() {
        return sharedPreferencesManager.isFirstTime();
    }

    public Boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
//        return sharedPreferencesManager.isUserLoggedIn();
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

package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local;

import com.google.firebase.auth.FirebaseAuth;

import io.github.boodyahmedhamdy.mealano.data.network.SharedPreferencesManager;

public class UsersLocalDataSource {
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
}

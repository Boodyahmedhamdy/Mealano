package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.users;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.SharedPreferencesManager;
import io.reactivex.rxjava3.core.Completable;

public class UsersLocalDataSourceImpl implements UsersLocalDataSource {
    private static final String TAG = "UsersLocalDataSource";
    SharedPreferencesManager sharedPreferencesManager;
    FirebaseAuth firebaseAuth;

    private UsersLocalDataSourceImpl(SharedPreferencesManager sharedPreferencesManager, FirebaseAuth firebaseAuth) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.firebaseAuth = firebaseAuth;
    }

    private static UsersLocalDataSource instance;

    public static UsersLocalDataSource getInstance(SharedPreferencesManager sharedPreferencesManager, FirebaseAuth firebaseAuth) {
        if(instance == null) {
            instance = new UsersLocalDataSourceImpl(sharedPreferencesManager, firebaseAuth);
        }
        return instance;
    }

    @Override
    public Boolean isFirstTime() {
        return sharedPreferencesManager.isFirstTime();
    }

    @Override
    public Boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    @Override
    public Completable signOut(GoogleSignInClient client) {
        return Completable.create(emitter -> {
           try {
               firebaseAuth.signOut();
               client.signOut().addOnSuccessListener(unused -> {
                   emitter.onComplete();
               }).addOnFailureListener(e -> {
                   emitter.onError(e);
               });
           } catch (Exception e) {
               emitter.onError(e);
           }
        });
    }
}

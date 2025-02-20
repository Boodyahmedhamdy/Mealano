package io.github.boodyahmedhamdy.mealano.datalayer.repos;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.UsersLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.UsersRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.login.contract.OnLoginCallBack;
import io.github.boodyahmedhamdy.mealano.profile.contract.OnSignOutCallback;
import io.github.boodyahmedhamdy.mealano.signup.contract.OnSignupCallback;

public class UsersRepository {

    private static final String TAG = "UsersRepository";
    UsersLocalDataSource localDataSource;
    UsersRemoteDataSource remoteDataSource;

    private static UsersRepository instance;

    public static UsersRepository getInstance(UsersLocalDataSource localDataSource, UsersRemoteDataSource remoteDataSource) {
        if(instance == null) {
            instance = new UsersRepository(localDataSource, remoteDataSource);
        }
        return instance;
    }


    private UsersRepository(UsersLocalDataSource localDataSource, UsersRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public void signupWithEmailAndPassword(String email, String password, OnSignupCallback callback) {
        Log.i(TAG, "signupWithEmailAndPassword: started");
        remoteDataSource.signupWithEmailAndPassword(email, password, callback);
        Log.i(TAG, "signupWithEmailAndPassword: finished");
    }

    public void loginWithEmailAndPassword(String email, String password, OnLoginCallBack callBack) {
        Log.i(TAG, "loginWithEmailAndPassword: started");
        remoteDataSource.loginWithEmailAndPassword(email, password, callBack);
        Log.i(TAG, "loginWithEmailAndPassword: finished");

    }

    public Boolean isFirstTime() {
        return localDataSource.isFirstTime();
    }

    public Boolean isLoggedIn() {
        return localDataSource.isLoggedIn();
    }

    public void signOut(OnSignOutCallback callback) {
        Log.i(TAG, "signOut: started");
        localDataSource.signOut(callback);
        Log.i(TAG, "signOut: finished");
    }

    public FirebaseUser getCurrentUser() {
        return localDataSource.getCurrentUser();
    }
}

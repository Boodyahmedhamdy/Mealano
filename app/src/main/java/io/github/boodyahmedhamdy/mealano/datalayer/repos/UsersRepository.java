package io.github.boodyahmedhamdy.mealano.datalayer.repos;

import android.util.Log;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.UsersLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.UsersRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.login.contract.OnLoginCallBack;
import io.github.boodyahmedhamdy.mealano.signup.contract.OnSignupCallback;

public class UsersRepository {

    private static final String TAG = "UsersRepository";
    UsersLocalDataSource localDataSource;
    UsersRemoteDataSource remoteDataSource;


    public UsersRepository(UsersLocalDataSource localDataSource, UsersRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public void signupWithEmailAndPassword(String email, String password, OnSignupCallback callback) {
        Log.i(TAG, "signupWithEmailAndPassword: started");
        remoteDataSource.signupWithEmailAndPassword(email, password, callback);
        Log.i(TAG, "signupWithEmailAndPassword: finished");
    }

    public void signInWithEmailAndPassword(String email, String password, OnLoginCallBack callBack) {
        remoteDataSource.signInWithEmailAndPassword(email, password, callBack);

    }
}

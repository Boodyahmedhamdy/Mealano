package io.github.boodyahmedhamdy.mealano.datalayer.repos;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.UsersLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.UsersRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.profile.contract.OnSignOutCallback;
import io.github.boodyahmedhamdy.mealano.utils.network.EmptyNetworkCallback;

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

    public Task<AuthResult> signupWithEmailAndPassword(String email, String password) {

        return remoteDataSource.signupWithEmailAndPassword(email, password);

    }

    public Task<AuthResult> loginWithEmailAndPassword(String email, String password) {
        return remoteDataSource.loginWithEmailAndPassword(email, password);

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

    public Task<AuthResult> signInWithCredential(AuthCredential credential) {
        return remoteDataSource.signupWithCredential(credential);
    }
}

package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.github.boodyahmedhamdy.mealano.utils.network.EmptyNetworkCallback;

public class UsersRemoteDataSource {
    private static final String TAG = "UsersRemoteDataSource";

    FirebaseAuth firebaseAuth;

    private UsersRemoteDataSource(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    private static UsersRemoteDataSource instance;

    public static UsersRemoteDataSource getInstance(FirebaseAuth firebaseAuth) {
        if(instance == null) {
            instance = new UsersRemoteDataSource(firebaseAuth);
        }
        return instance;
    }

    public Task<AuthResult> signupWithEmailAndPassword(String email, String password) {
        Log.i(TAG, "signupWithEmailAndPassword: started");
        return firebaseAuth.createUserWithEmailAndPassword(
                email, password
        );
    }

    public Task<AuthResult> loginWithEmailAndPassword(String email, String password) {
        Log.i(TAG, "loginWithEmailAndPassword: started");

        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }
}

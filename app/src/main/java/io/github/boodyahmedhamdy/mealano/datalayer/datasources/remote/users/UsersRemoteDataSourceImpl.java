package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.users;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UsersRemoteDataSourceImpl implements UsersRemoteDataSource {
    private static final String TAG = "UsersRemoteDataSource";

    FirebaseAuth firebaseAuth;

    private UsersRemoteDataSourceImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    private static UsersRemoteDataSource instance;

    public static UsersRemoteDataSource getInstance(FirebaseAuth firebaseAuth) {
        if(instance == null) {
            instance = new UsersRemoteDataSourceImpl(firebaseAuth);
        }
        return instance;
    }

    @Override
    public Task<AuthResult> signupWithEmailAndPassword(String email, String password) {
        Log.i(TAG, "signupWithEmailAndPassword: started");
        return firebaseAuth.createUserWithEmailAndPassword(
                email, password
        );
    }

    @Override
    public Task<AuthResult> loginWithEmailAndPassword(String email, String password) {
        Log.i(TAG, "loginWithEmailAndPassword: started");

        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    @Override
    public Task<AuthResult> signupWithCredential(AuthCredential credential) {
        return firebaseAuth.signInWithCredential(credential);
    }
}

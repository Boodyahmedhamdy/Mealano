package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.github.boodyahmedhamdy.mealano.login.contract.OnLoginCallBack;
import io.github.boodyahmedhamdy.mealano.signup.contract.OnSignupCallback;

public class UsersRemoteDataSource {
    private static final String TAG = "UsersRemoteDataSource";

    FirebaseAuth firebaseAuth;

    public UsersRemoteDataSource(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public void signupWithEmailAndPassword(String email, String password, OnSignupCallback callback) {
        Log.i(TAG, "signupWithEmailAndPassword: started");
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i(TAG, "signupWithEmailAndPassword: Completed");
                        if(task.isSuccessful()) {
                            callback.onSuccess();
                            Log.i(TAG, "signupWithEmailAndPassword: Completed Successfully");
                        } else {
                            callback.onFailure(task.getException().getLocalizedMessage());
                            Log.i(TAG, "signupWithEmailAndPassword: Completed with Error");
                        }
                    }
                }
        );
    }

    public void loginWithEmailAndPassword(String email, String password, OnLoginCallBack callBack) {
        Log.i(TAG, "loginWithEmailAndPassword: started");
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i(TAG, "onComplete: completed");
                if(task.isSuccessful()) {
                    callBack.onSuccess();
                    Log.i(TAG, "onComplete: Completed Successfully");
                } else {
                    callBack.onFailure(task.getException().getLocalizedMessage());
                    Log.e(TAG, "onComplete: Completed with Error");
                }
            }
        });
    }
}

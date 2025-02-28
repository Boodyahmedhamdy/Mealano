package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.users;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;

public interface UsersRemoteDataSource {
    Task<AuthResult> signupWithEmailAndPassword(String email, String password);

    Task<AuthResult> loginWithEmailAndPassword(String email, String password);

    Task<AuthResult> signupWithCredential(AuthCredential credential);
}

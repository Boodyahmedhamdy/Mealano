package io.github.boodyahmedhamdy.mealano.datalayer.repos.users;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.core.Completable;

public interface UsersRepository {
    Task<AuthResult> signupWithEmailAndPassword(String email, String password);

    Task<AuthResult> loginWithEmailAndPassword(String email, String password);

    Boolean isFirstTime();

    Boolean isLoggedIn();

    FirebaseUser getCurrentUser();

    Task<AuthResult> signInWithCredential(AuthCredential credential);

    Completable signOut(GoogleSignInClient client);
}

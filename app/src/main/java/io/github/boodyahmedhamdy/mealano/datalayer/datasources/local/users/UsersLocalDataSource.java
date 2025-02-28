package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.users;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.core.Completable;

public interface UsersLocalDataSource {
    Boolean isFirstTime();

    Boolean isLoggedIn();

    FirebaseUser getCurrentUser();

    Completable signOut(GoogleSignInClient client);
}

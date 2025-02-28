package io.github.boodyahmedhamdy.mealano.profile.contract;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface ProfilePresenter {
    void signOut(GoogleSignInClient client);

    void getCurrentUser();
}

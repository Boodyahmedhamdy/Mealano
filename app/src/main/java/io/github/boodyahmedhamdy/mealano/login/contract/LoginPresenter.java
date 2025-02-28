package io.github.boodyahmedhamdy.mealano.login.contract;

import com.google.firebase.auth.AuthCredential;

public interface LoginPresenter {
    void loginWithEmailAndPassword(String email, String password);

    void signInWithCredential(AuthCredential credential);
}

package io.github.boodyahmedhamdy.mealano.profile.contract;

import com.google.firebase.auth.FirebaseUser;

public interface ProfileView {
    void setCurrentUser(FirebaseUser user);
}

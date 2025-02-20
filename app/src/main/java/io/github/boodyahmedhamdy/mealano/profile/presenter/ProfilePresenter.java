package io.github.boodyahmedhamdy.mealano.profile.presenter;

import android.util.Log;

import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.profile.contract.OnSignOutCallback;
import io.github.boodyahmedhamdy.mealano.profile.contract.ProfileView;

public class ProfilePresenter {
    private static final String TAG = "ProfilePresenter";
    ProfileView view;
    UsersRepository usersRepository;

    public ProfilePresenter(ProfileView view, UsersRepository usersRepository) {
        this.view = view;
        this.usersRepository = usersRepository;
    }

    public void signOut(OnSignOutCallback callback) {
        Log.i(TAG, "signOut: started");
        usersRepository.signOut(callback);
        Log.i(TAG, "signOut: finished");
    }

    public void getCurrentUser() {
        view.setCurrentUser(usersRepository.getCurrentUser());
    }
}

package io.github.boodyahmedhamdy.mealano.profile.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import io.github.boodyahmedhamdy.mealano.datalayer.repos.users.UsersRepository;
import io.github.boodyahmedhamdy.mealano.profile.contract.ProfilePresenter;
import io.github.boodyahmedhamdy.mealano.profile.contract.ProfileView;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ProfilePresenterImpl implements ProfilePresenter {
    private static final String TAG = "ProfilePresenter";
    ProfileView view;
    UsersRepository usersRepository;

    public ProfilePresenterImpl(ProfileView view, UsersRepository usersRepository) {
        this.view = view;
        this.usersRepository = usersRepository;
    }

    @Override
    public void signOut(GoogleSignInClient client) {
        Disposable dis =  usersRepository.signOut(client)
                .compose(new OnBackgroundTransformer<>())
                .subscribe(() -> {
                    view.goToLogin();
                }, throwable -> {
                    view.setErrorMessage(throwable.getLocalizedMessage());
                });
    }

    @Override
    public void getCurrentUser() {
        view.setCurrentUser(usersRepository.getCurrentUser());
    }
}

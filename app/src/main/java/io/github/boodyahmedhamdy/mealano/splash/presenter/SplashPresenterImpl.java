package io.github.boodyahmedhamdy.mealano.splash.presenter;

import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.splash.contract.SplashPresenter;
import io.github.boodyahmedhamdy.mealano.splash.contract.SplashView;

public class SplashPresenterImpl implements SplashPresenter {

    SplashView view;
    UsersRepository usersRepository;

    public SplashPresenterImpl(SplashView view, UsersRepository usersRepository) {
        this.view = view;
        this.usersRepository = usersRepository;
    }

    @Override
    public void start() {
        if(usersRepository.isFirstTime()) {
            view.goToOnBoarding();
        } else if(usersRepository.isLoggedIn()) {
            view.goToHome();
        } else {
            view.goToLogin();
        }
    }
}

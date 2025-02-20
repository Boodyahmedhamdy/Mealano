package io.github.boodyahmedhamdy.mealano.splash.presenter;

import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.splash.contract.SplashView;

public class SplashPresenter {

    SplashView view;
    UsersRepository usersRepository;

    public SplashPresenter(SplashView view, UsersRepository usersRepository) {
        this.view = view;
        this.usersRepository = usersRepository;
    }

    public boolean isFirstTime() {
        return usersRepository.isFirstTime();
    }

    public boolean isLoggedIn() {
        return usersRepository.isLoggedIn();
    }
}

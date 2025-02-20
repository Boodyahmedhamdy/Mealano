package io.github.boodyahmedhamdy.mealano.home.presenter;

import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.home.contract.HomeView;
import io.github.boodyahmedhamdy.mealano.home.contract.OnRandomMealReceivedCallback;

public class HomePresenter {

    MealsRepository mealsRepository;
    HomeView view;

    public HomePresenter(HomeView view, MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }

    public void getRandomMeal(OnRandomMealReceivedCallback callback) {

        mealsRepository.getRandomMeal(callback);

    }
}

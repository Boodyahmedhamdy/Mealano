package io.github.boodyahmedhamdy.mealano.favorite.presenter;

import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.favorite.contract.FavoriteView;

public class FavoritePresenter {

    FavoriteView view;
    MealsRepository mealsRepository;

    public FavoritePresenter(FavoriteView view, MealsRepository mealsRepository) {
        this.view = view;
        this.mealsRepository = mealsRepository;
    }

    public void getFavoriteMeals() {
//        view.setFavoriteMeals();

    }
}

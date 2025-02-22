package io.github.boodyahmedhamdy.mealano.favorite.presenter;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.favorite.contract.FavoriteView;

public class FavoritePresenter {

    FavoriteView view;
    MealsRepository mealsRepository;
    UsersRepository usersRepository;

    public FavoritePresenter(FavoriteView view, MealsRepository mealsRepository, UsersRepository usersRepository) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.usersRepository = usersRepository;
    }

    public LiveData<List<MealEntity>> getFavoriteMeals() {
        return mealsRepository.getFavoriteMeals(
                usersRepository.getCurrentUser().getUid()
        );
    }

    public void deleteFavoriteMeal(MealEntity meal) {
        mealsRepository.deleteFavoriteMeal(meal);
    }
}

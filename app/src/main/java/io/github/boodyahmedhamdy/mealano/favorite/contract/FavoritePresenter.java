package io.github.boodyahmedhamdy.mealano.favorite.contract;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;

public interface FavoritePresenter {
    void getFavoriteMeals();

    void deleteFavoriteMeal(MealEntity meal);
}

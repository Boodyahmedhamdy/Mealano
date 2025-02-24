package io.github.boodyahmedhamdy.mealano.favorite.contract;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;

public interface FavoriteView {

    void setFavoriteMeals(List<MealEntity> meals);

    void goToDetailsScreen();


    void setErrorMessage(String errorMessage);
}

package io.github.boodyahmedhamdy.mealano.favorite.contract;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;

public interface FavoriteView {

    void setFavoriteMeals(LiveData<List<MealEntity>> meals);


}

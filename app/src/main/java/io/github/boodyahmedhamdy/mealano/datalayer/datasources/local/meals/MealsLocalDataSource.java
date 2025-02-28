package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.meals;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsLocalDataSource {
    Completable addMealToFavorite(MealEntity mealEntity);

    Flowable<List<MealEntity>> getFavoriteMeals(String userId);

    Completable deleteFavoriteMeal(MealEntity meal);

    Single<MealEntity> getMealById(String mealId);
}

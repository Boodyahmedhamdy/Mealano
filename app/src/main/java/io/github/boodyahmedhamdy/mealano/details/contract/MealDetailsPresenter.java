package io.github.boodyahmedhamdy.mealano.details.contract;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;

public interface MealDetailsPresenter {
    void getMealById(String mealId);

    void addMealToFavorite(DetailedMealDTO mealDTO);

    void addMealToPlans(DetailedMealDTO currentMeal, Long date);
}

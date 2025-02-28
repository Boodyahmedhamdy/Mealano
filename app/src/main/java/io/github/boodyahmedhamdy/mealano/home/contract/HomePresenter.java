package io.github.boodyahmedhamdy.mealano.home.contract;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;

public interface HomePresenter {
    void getRandomMeal();

    void getAllMeals();

    void getMealById(String mealId);

    void addMealToPlans(DetailedMealDTO mealDTO, Long date);

}

package io.github.boodyahmedhamdy.mealano.home.contract;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;

public interface HomeView {
    void setRandomMeal(DetailedMealDTO detailedMealDTO);
    void setAllMeals(List<DetailedMealDTO> allMeals);

    void goToMeal(Integer mealId);

    void setError(String errorMessage);

    void setIsLoading(Boolean isLoading);
    void setIsOnline(Boolean isOnline);
}

package io.github.boodyahmedhamdy.mealano.home.contract;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;

public interface HomeView {
    void setRandomMeal(DetailedMealDTO detailedMealDTO);
    void setAllMeals(List<DetailedMealDTO> allMeals);

    void goToMeal(String mealId);

    void setError(String errorMessage);

    void setIsLoading(Boolean isLoading);
    void setIsOnline(Boolean isOnline);

    void setSuccessMessage(String successMessage);
}

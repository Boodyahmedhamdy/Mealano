package io.github.boodyahmedhamdy.mealano.details.contract;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;

public interface MealDetailsView {

    void setMeal(DetailedMealDTO mealDTO);
    void setErrorMessage(String errorMessage);
    void setIsLoading(Boolean isLoading);
    void setIsOnline(Boolean isOnline);


}

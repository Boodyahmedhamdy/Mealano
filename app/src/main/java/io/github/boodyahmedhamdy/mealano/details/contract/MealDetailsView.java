package io.github.boodyahmedhamdy.mealano.details.contract;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;

public interface MealDetailsView {

    void setMeal(DetailedMealDTO mealDTO);
    void setErrorMessage(String errorMessage);

    void setSuccessMessage(String successMessage);
    void setIsLoading(Boolean isLoading);
    void setIsOnline(Boolean isOnline);


    void setSuccessfullyAddedToFavorite(DetailedMealDTO mealDTO);

    void setSuccessfullyAddedToPlans(boolean isAddedToPlans);
}

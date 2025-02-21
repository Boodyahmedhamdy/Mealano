package io.github.boodyahmedhamdy.mealano.home.contract;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;

public interface OnMealClickedCallback {
    void onSuccess(DetailedMealDTO mealDTO);
    void onFailure(String errorMessage);
}

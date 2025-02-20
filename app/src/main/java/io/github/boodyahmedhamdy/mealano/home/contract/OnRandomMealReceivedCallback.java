package io.github.boodyahmedhamdy.mealano.home.contract;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;

public interface OnRandomMealReceivedCallback {
    void onSuccess(DetailedMealDTO detailedMealDTO);
    void onFailure(String errorMessage);
}

package io.github.boodyahmedhamdy.mealano.home.contract;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;

public interface OnAllMealsReceivedCallback {
    void onSuccess(List<DetailedMealDTO> meals);
    void onFailure(String errorMessage);
}

package io.github.boodyahmedhamdy.mealano.searchby.contract;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.SimpleMealDTO;

public interface SearchByView {
    void setIsOnline(boolean isOnline);

    void setErrorMessage(String errorMessage);

    void setList(List<SimpleMealDTO> simpleMealDTOS);

    void setIsLoading(boolean isLoading);
}

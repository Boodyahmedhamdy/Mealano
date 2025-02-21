package io.github.boodyahmedhamdy.mealano.details.presenter;

import android.util.Log;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.details.contract.IMealDetailsPresenter;
import io.github.boodyahmedhamdy.mealano.details.contract.MealDetailsView;
import io.github.boodyahmedhamdy.mealano.home.contract.OnMealClickedCallback;
import io.github.boodyahmedhamdy.mealano.home.contract.OnRandomMealReceivedCallback;

public class MealDetailsPresenter implements IMealDetailsPresenter, OnRandomMealReceivedCallback {

    private static final String TAG = "MealDetailsPresenter";
    MealDetailsView view;
    MealsRepository mealsRepository;

    public MealDetailsPresenter(MealDetailsView view, MealsRepository mealsRepository) {
        this.view = view;
        this.mealsRepository = mealsRepository;
    }


    @Override
    public void getMealById(Integer mealId) {
        mealsRepository.getMealById(mealId, new OnMealClickedCallback() {
            @Override
            public void onSuccess(DetailedMealDTO mealDTO) {
                Log.i(TAG, "OnMealClickedCallback.onSuccess: " + mealDTO);
                view.setMeal(mealDTO);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "OnMealClickedCallback.onFailure: " + errorMessage);
                view.setErrorMessage("error from Presenter: " + errorMessage);
            }
        });
    }

    @Override
    public void onSuccess(DetailedMealDTO detailedMealDTO) {
        view.setMeal(detailedMealDTO);
    }

    @Override
    public void onFailure(String errorMessage) {
        view.setErrorMessage(errorMessage);
    }
}

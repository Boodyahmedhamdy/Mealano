package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote;

import android.util.Log;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealsResponse;
import io.github.boodyahmedhamdy.mealano.home.contract.OnAllMealsReceivedCallback;
import io.github.boodyahmedhamdy.mealano.home.contract.OnMealClickedCallback;
import io.github.boodyahmedhamdy.mealano.home.contract.OnRandomMealReceivedCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsRemoteDataSource {

    private static final String TAG = "MealsRemoteDataSource";
    MealsApiService service;

    public MealsRemoteDataSource(MealsApiService service) {
        this.service = service;
    }

    public void getRandomMeal(OnRandomMealReceivedCallback callback) {
        service.getRandomMeal().enqueue(new Callback<DetailedMealsResponse>() {
            @Override
            public void onResponse(Call<DetailedMealsResponse> call, Response<DetailedMealsResponse> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getMeals().get(0));
                } else {
                    callback.onFailure("Failed to get Response");
                }
            }

            @Override
            public void onFailure(Call<DetailedMealsResponse> call, Throwable throwable) {
                callback.onFailure(throwable.getLocalizedMessage());
            }
        });

    }

    public void getAllMeals(OnAllMealsReceivedCallback callback) {
        service.getAllMeals().enqueue(new Callback<DetailedMealsResponse>() {
            @Override
            public void onResponse(Call<DetailedMealsResponse> call, Response<DetailedMealsResponse> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getMeals());
                } else {
                    callback.onFailure("Error: response isn't successful. code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DetailedMealsResponse> call, Throwable throwable) {
                callback.onFailure(throwable.getLocalizedMessage());
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
    }

    public void getMealById(Integer mealId, OnMealClickedCallback callback) {
        service.getMealById(mealId).enqueue(new Callback<DetailedMealsResponse>() {
            @Override
            public void onResponse(Call<DetailedMealsResponse> call, Response<DetailedMealsResponse> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getMeals().get(0));
                } else {
                    callback.onFailure("error from api with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DetailedMealsResponse> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
                callback.onFailure(throwable.getLocalizedMessage());
            }
        });
    }
}

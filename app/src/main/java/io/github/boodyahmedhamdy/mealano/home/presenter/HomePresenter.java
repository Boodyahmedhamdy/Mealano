package io.github.boodyahmedhamdy.mealano.home.presenter;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.home.contract.HomeView;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.github.boodyahmedhamdy.mealano.utils.network.CustomNetworkCallback;

public class HomePresenter {
    private static final String TAG = "HomePresenter";

    HomeView view;
    MealsRepository mealsRepository;
    UsersRepository usersRepository;

    public HomePresenter(HomeView view, MealsRepository mealsRepository, UsersRepository usersRepository) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.usersRepository = usersRepository;
    }

    public void getRandomMeal() {
        view.setIsLoading(true);
        mealsRepository.getRandomMeal(new CustomNetworkCallback<DetailedMealDTO>() {
            @Override
            public void onSuccess(DetailedMealDTO detailedMealDTO) {
                view.setRandomMeal(detailedMealDTO);
                view.setIsLoading(false);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.setError(errorMessage);
                view.setIsLoading(false);
            }
        });
    }

    public void getAllMeals() {
        view.setIsLoading(true);
        mealsRepository.getAllMeals(new CustomNetworkCallback<List<DetailedMealDTO>>() {
            @Override
            public void onSuccess(List<DetailedMealDTO> meals) {
                Collections.shuffle(meals);
                view.setAllMeals(meals);
                view.setIsLoading(false);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.setError(errorMessage);
                view.setIsLoading(false);
            }
        });
    }

    public void getMealById(Integer mealId) {
        mealsRepository.getMealById(mealId, new CustomNetworkCallback<DetailedMealDTO>() {
            @Override
            public void onSuccess(DetailedMealDTO mealDTO) {
                view.goToMeal(mealDTO.getIdMeal());
            }

            @Override
            public void onFailure(String errorMessage) {
                view.setError(errorMessage);
            }
        });
    }

    public void addMealToFavorite(DetailedMealDTO mealDTO) {
        mealsRepository.addMealToFavorite(
                mealDTO, usersRepository.getCurrentUser().getUid(),
                new CustomCallback<DetailedMealDTO>() {
                    @Override
                    public void onSuccess(DetailedMealDTO data) {
                        Log.i(TAG, "onSuccess: ");
                        view.setSuccessfullyAddedToFavorite(data);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.i(TAG, "onFailure: ");
                    }
                }
        );
    }



}

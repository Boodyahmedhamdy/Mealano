package io.github.boodyahmedhamdy.mealano.home.presenter;

import java.util.Collections;
import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.home.contract.HomeView;
import io.github.boodyahmedhamdy.mealano.utils.network.CustomNetworkCallback;

public class HomePresenter {

    MealsRepository mealsRepository;
    HomeView view;

    public HomePresenter(HomeView view, MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
        this.view = view;
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
                view.goToMeal(Integer.valueOf(mealDTO.getIdMeal()));
            }

            @Override
            public void onFailure(String errorMessage) {
                view.setError(errorMessage);
            }
        });
    }



}

package io.github.boodyahmedhamdy.mealano.home.presenter;

import java.util.Collections;
import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.home.contract.HomeView;
import io.github.boodyahmedhamdy.mealano.home.contract.OnAllMealsReceivedCallback;
import io.github.boodyahmedhamdy.mealano.home.contract.OnMealClickedCallback;
import io.github.boodyahmedhamdy.mealano.home.contract.OnRandomMealReceivedCallback;

public class HomePresenter {

    MealsRepository mealsRepository;
    HomeView view;

    public HomePresenter(HomeView view, MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }

    public void getRandomMeal() {
        mealsRepository.getRandomMeal(new OnRandomMealReceivedCallback() {
            @Override
            public void onSuccess(DetailedMealDTO detailedMealDTO) {
                view.setRandomMeal(detailedMealDTO);

            }

            @Override
            public void onFailure(String errorMessage) {
                view.setError(errorMessage);
            }
        });
    }

    public void getAllMeals() {
        mealsRepository.getAllMeals(new OnAllMealsReceivedCallback() {
            @Override
            public void onSuccess(List<DetailedMealDTO> meals) {
                Collections.shuffle(meals);
                view.setAllMeals(meals);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.setError(errorMessage);
            }
        });
    }

    public void getMealById(Integer mealId) {
        mealsRepository.getMealById(mealId, new OnMealClickedCallback() {
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

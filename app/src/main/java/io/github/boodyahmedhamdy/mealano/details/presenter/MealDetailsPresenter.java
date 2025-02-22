package io.github.boodyahmedhamdy.mealano.details.presenter;

import android.util.Log;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.details.contract.IMealDetailsPresenter;
import io.github.boodyahmedhamdy.mealano.details.contract.MealDetailsView;
import io.github.boodyahmedhamdy.mealano.utils.network.CustomNetworkCallback;

public class MealDetailsPresenter implements IMealDetailsPresenter {

    private static final String TAG = "MealDetailsPresenter";
    MealDetailsView view;
    MealsRepository mealsRepository;

    UsersRepository usersRepository;

    public MealDetailsPresenter(MealDetailsView view, MealsRepository mealsRepository, UsersRepository usersRepository) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.usersRepository = usersRepository;
    }


    @Override
    public void getMealById(Integer mealId) {
        view.setIsLoading(true);
        mealsRepository.getMealById(mealId, new CustomNetworkCallback<DetailedMealDTO>() {
            @Override
            public void onSuccess(DetailedMealDTO mealDTO) {
                Log.i(TAG, "onSuccess: " + mealDTO);
                view.setMeal(mealDTO);
                view.setIsLoading(false);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "onFailure: " + errorMessage);
                view.setErrorMessage("error from Presenter: " + errorMessage);
                view.setIsLoading(false);
            }
        });
    }

    public void addMealToFavorite(DetailedMealDTO mealDTO) {
        Log.i(TAG, "addMealToFavorite: started adding to favorite");
        mealsRepository.addMealToFavorite(mealDTO, usersRepository.getCurrentUser().getUid());
        Log.i(TAG, "addMealToFavorite: finished adding to favorite");
    }
}

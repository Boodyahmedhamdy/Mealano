package io.github.boodyahmedhamdy.mealano.datalayer.repos;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.MealsLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.MealsRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.EmptyCallback;
import io.github.boodyahmedhamdy.mealano.utils.network.CustomNetworkCallback;

public class MealsRepository {
    
    MealsLocalDataSource localDataSource;
    MealsRemoteDataSource remoteDataSource;

    private static final String TAG = "MealsRepository";

    private static MealsRepository instance;
    public static MealsRepository getInstance(MealsLocalDataSource localDataSource, MealsRemoteDataSource remoteDataSource) {
        if (instance == null) {
            instance = new MealsRepository(localDataSource, remoteDataSource);
        }
        return instance;
    }

    private MealsRepository(MealsLocalDataSource localDataSource, MealsRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }


    public void getRandomMeal(CustomNetworkCallback<DetailedMealDTO> callback) {
        remoteDataSource.getRandomMeal(callback);
    }

    public void getAllMeals(CustomNetworkCallback<List<DetailedMealDTO>> callback) {
        remoteDataSource.getAllMeals(callback);
    }

    public void getMealById(Integer mealId, CustomNetworkCallback<DetailedMealDTO> callback) {
        remoteDataSource.getMealById(mealId, callback);
    }

    public void addMealToFavorite(DetailedMealDTO mealDTO, String userId, CustomCallback<DetailedMealDTO> callback) {
        Log.i(TAG, "addMealToFavorite: started");
        localDataSource.addMealToFavorite(mealDTO, userId, callback);
        Log.i(TAG, "addMealToFavorite: finished");
        remoteDataSource.addToFavorite(mealDTO, userId, callback);
    }

    public LiveData<List<MealEntity>> getFavoriteMeals(String userId) {
        return localDataSource.getFavoriteMeals(userId);
    }

    public void deleteFavoriteMeal(MealEntity meal) {
        localDataSource.deleteFavoriteMeal(meal);
    }


}

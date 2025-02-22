package io.github.boodyahmedhamdy.mealano.datalayer.repos;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.MealsLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.MealsRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.utils.network.CustomNetworkCallback;

public class MealsRepository {
    
    MealsLocalDataSource localDataSource;
    MealsRemoteDataSource remoteDataSource;

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
}

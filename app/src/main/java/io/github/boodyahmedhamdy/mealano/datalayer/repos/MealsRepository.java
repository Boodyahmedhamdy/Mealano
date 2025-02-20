package io.github.boodyahmedhamdy.mealano.datalayer.repos;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.MealsLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.MealsRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.home.contract.OnRandomMealReceivedCallback;

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


    public void getRandomMeal(OnRandomMealReceivedCallback callback) {
        remoteDataSource.getRandomMeal(callback);
    }
}

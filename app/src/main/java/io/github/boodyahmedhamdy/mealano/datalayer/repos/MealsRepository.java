package io.github.boodyahmedhamdy.mealano.datalayer.repos;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealsResponse;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.MealsLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.MealsRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

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


    public Single<DetailedMealsResponse> getRandomMeal() {
        return remoteDataSource.getRandomMeal();
    }

    public Single<DetailedMealsResponse> getAllMeals() {
        return remoteDataSource.getAllMeals();
    }

    public Single<DetailedMealsResponse> getMealById(String mealId) {
        return remoteDataSource.getMealById(mealId);
    }

    public Completable addMealToFavorite(MealEntity mealEntity) {
        return localDataSource.addMealToFavorite(mealEntity);

//        remoteDataSource.addToFavorite(mealDTO, userId, callback);
    }

    public Flowable<List<MealEntity>> getFavoriteMeals(String userId) {
        return localDataSource.getFavoriteMeals(userId);
    }

    public Completable deleteFavoriteMeal(MealEntity meal) {
        return localDataSource.deleteFavoriteMeal(meal);
    }


}

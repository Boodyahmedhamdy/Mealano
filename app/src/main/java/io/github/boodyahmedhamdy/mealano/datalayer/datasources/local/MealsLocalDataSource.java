package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos.MealsDao;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.EmptyCallback;

public class MealsLocalDataSource {

    private static final String TAG = "MealsLocalDataSource";
    MealsDao mealsDao;

    private MealsLocalDataSource(MealsDao mealsDao) {
        this.mealsDao = mealsDao;
    }

    private static MealsLocalDataSource instance;
    public static MealsLocalDataSource getInstance(MealsDao mealsDao) {
        if(instance == null) {
            instance = new MealsLocalDataSource(mealsDao);
        }
        return instance;
    }


    public void addMealToFavorite(DetailedMealDTO mealDTO, String userId, CustomCallback<DetailedMealDTO> callback) {
        Log.i(TAG, "addMealToFavorite: started");
        MealEntity entity = new MealEntity(mealDTO, userId);
        try {
            new Thread(() -> {
                Log.i(TAG, "addMealToFavorite: started on thread");
                mealsDao.insertMeal(entity);
                Log.i(TAG, "addMealToFavorite: finished on thread");
            }).start();
            callback.onSuccess(mealDTO);
        } catch (Exception e) {
            callback.onFailure(e.getLocalizedMessage());
        }
    }

    public LiveData<List<MealEntity>> getFavoriteMeals(String userId) {

        return mealsDao.getAllMeals(userId);
    }

    public void deleteFavoriteMeal(MealEntity meal) {
        new Thread(() -> {
            mealsDao.deleteMeal(meal);
        }).start();
    }
}

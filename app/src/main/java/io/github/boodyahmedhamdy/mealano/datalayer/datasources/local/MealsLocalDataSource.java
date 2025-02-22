package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local;

import android.util.Log;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos.MealsDao;

public class MealsLocalDataSource {

    private static final String TAG = "MealsLocalDataSource";
    MealsDao mealsDao;

    public MealsLocalDataSource(MealsDao mealsDao) {
        this.mealsDao = mealsDao;
    }


    public void addMealToFavorite(DetailedMealDTO mealDTO, String userId) {
        Log.i(TAG, "addMealToFavorite: started");
        MealEntity entity = new MealEntity(mealDTO, userId);
        new Thread(() -> {
            Log.i(TAG, "addMealToFavorite: started on thread");
            mealsDao.insertMeal(entity);
            Log.i(TAG, "addMealToFavorite: finished on thread");
        }).start();
    }
}

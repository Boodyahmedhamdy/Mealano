package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos.MealsDao;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.EmptyCallback;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

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


    public Completable addMealToFavorite(MealEntity mealEntity) {
        return mealsDao.insertMeal(mealEntity);
    }

    public Flowable<List<MealEntity>> getFavoriteMeals(String userId) {

        return mealsDao.getAllMeals(userId);
    }

    public Completable deleteFavoriteMeal(MealEntity meal) {
            return mealsDao.deleteMeal(meal);
    }
}

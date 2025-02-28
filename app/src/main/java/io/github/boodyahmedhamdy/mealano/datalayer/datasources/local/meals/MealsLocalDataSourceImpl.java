package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.meals;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos.MealsDao;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {

    private static final String TAG = "MealsLocalDataSource";
    MealsDao mealsDao;

    private MealsLocalDataSourceImpl(MealsDao mealsDao) {
        this.mealsDao = mealsDao;
    }

    private static MealsLocalDataSource instance;
    public static MealsLocalDataSource getInstance(MealsDao mealsDao) {
        if(instance == null) {
            instance = new MealsLocalDataSourceImpl(mealsDao);
        }
        return instance;
    }


    @Override
    public Completable addMealToFavorite(MealEntity mealEntity) {
        return mealsDao.insertMeal(mealEntity);
    }

    @Override
    public Flowable<List<MealEntity>> getFavoriteMeals(String userId) {

        return mealsDao.getAllMeals(userId);
    }

    @Override
    public Completable deleteFavoriteMeal(MealEntity meal) {
            return mealsDao.deleteMeal(meal);
    }

    @Override
    public Single<MealEntity> getMealById(String mealId) {
        return mealsDao.getMealById(mealId);
    }
}

package io.github.boodyahmedhamdy.mealano.datalayer.repos;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

import io.github.boodyahmedhamdy.mealano.data.network.dto.CategoriesResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.IngredientsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.SimpleAreasResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.SimpleMealsResponse;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.MealsLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.MealsRemoteDataSource;
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


    public Single<CategoriesResponse> getAllCategories() {
        return remoteDataSource.getAllCategories();
    }

    public Single<IngredientsResponse> getAllIngredients() {
        return remoteDataSource.getAllIngredients();
    }

    public Single<SimpleAreasResponse> getAllAreas() {
        return remoteDataSource.getAllAreas();
    }

    public Single<SimpleMealsResponse> getAllMealsByIngredient(String ingredient) {
        return remoteDataSource.getAllMealsByIngredient(ingredient);
    }

    public Single<SimpleMealsResponse> getAllMealsByCategory(String category) {
        return remoteDataSource.getAllMealsByCategory(category);
    }

    public Single<SimpleMealsResponse> getAllMealsByArea(String area) {
        return remoteDataSource.getAllMealsByArea(area);
    }
}

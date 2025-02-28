package io.github.boodyahmedhamdy.mealano.datalayer.repos.meals;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.responses.CategoriesResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.DetailedMealsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.IngredientsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.SimpleAreasResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.SimpleMealsResponse;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.meals.MealsLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.meals.MealsRemoteDataSource;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepositoryImpl implements MealsRepository {
    
    MealsLocalDataSource localDataSource;
    MealsRemoteDataSource remoteDataSource;

    private static final String TAG = "MealsRepository";

    private static MealsRepository instance;
    public static MealsRepository getInstance(MealsLocalDataSource localDataSource, MealsRemoteDataSource remoteDataSource) {
        if (instance == null) {
            instance = new MealsRepositoryImpl(localDataSource, remoteDataSource);
        }
        return instance;
    }

    private MealsRepositoryImpl(MealsLocalDataSource localDataSource, MealsRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }


    @Override
    public Single<DetailedMealsResponse> getRandomMeal() {
        return remoteDataSource.getRandomMeal();
    }

    @Override
    public Single<DetailedMealsResponse> getAllMeals() {
        return remoteDataSource.getAllMeals();
    }

    @Override
    public Single<DetailedMealsResponse> getMealByIdFromRemote(String mealId) {
        return remoteDataSource.getMealById(mealId);
    }

    @Override
    public Completable addMealToFavorite(MealEntity mealEntity) {
        return localDataSource.addMealToFavorite(mealEntity);
    }

    @Override
    public Flowable<List<MealEntity>> getFavoriteMeals(String userId) {
        return localDataSource.getFavoriteMeals(userId);
    }

    @Override
    public Completable deleteFavoriteMeal(MealEntity meal) {
        return localDataSource.deleteFavoriteMeal(meal);
    }


    @Override
    public Single<CategoriesResponse> getAllCategories() {
        return remoteDataSource.getAllCategories();
    }

    @Override
    public Single<IngredientsResponse> getAllIngredients() {
        return remoteDataSource.getAllIngredients();
    }

    @Override
    public Single<SimpleAreasResponse> getAllAreas() {
        return remoteDataSource.getAllAreas();
    }

    @Override
    public Single<SimpleMealsResponse> getAllMealsByIngredient(String ingredient) {
        return remoteDataSource.getAllMealsByIngredient(ingredient);
    }

    @Override
    public Single<SimpleMealsResponse> getAllMealsByCategory(String category) {
        return remoteDataSource.getAllMealsByCategory(category);
    }

    @Override
    public Single<SimpleMealsResponse> getAllMealsByArea(String area) {
        return remoteDataSource.getAllMealsByArea(area);
    }

    @Override
    public Single<MealEntity> getMealByIdFromLocal(String mealId) {
        return localDataSource.getMealById(mealId);
    }
}

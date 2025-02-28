package io.github.boodyahmedhamdy.mealano.datalayer.repos.meals;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.responses.CategoriesResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.DetailedMealsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.IngredientsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.SimpleAreasResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.SimpleMealsResponse;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsRepository {
    Single<DetailedMealsResponse> getRandomMeal();

    Single<DetailedMealsResponse> getAllMeals();

    Single<DetailedMealsResponse> getMealByIdFromRemote(String mealId);

    Completable addMealToFavorite(MealEntity mealEntity);

    Flowable<List<MealEntity>> getFavoriteMeals(String userId);

    Completable deleteFavoriteMeal(MealEntity meal);

    Single<CategoriesResponse> getAllCategories();

    Single<IngredientsResponse> getAllIngredients();

    Single<SimpleAreasResponse> getAllAreas();

    Single<SimpleMealsResponse> getAllMealsByIngredient(String ingredient);

    Single<SimpleMealsResponse> getAllMealsByCategory(String category);

    Single<SimpleMealsResponse> getAllMealsByArea(String area);

    Single<MealEntity> getMealByIdFromLocal(String mealId);
}

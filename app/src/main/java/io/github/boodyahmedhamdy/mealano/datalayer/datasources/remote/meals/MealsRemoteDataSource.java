package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.meals;

import io.github.boodyahmedhamdy.mealano.data.network.responses.CategoriesResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.data.network.responses.DetailedMealsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.IngredientsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.SimpleAreasResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.SimpleMealsResponse;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSource {
    Single<DetailedMealsResponse> getRandomMeal();

    Single<DetailedMealsResponse> getAllMeals();

    Single<DetailedMealsResponse> getMealById(String mealId);

    void addToFavorite(DetailedMealDTO mealDTO, String userId, CustomCallback<DetailedMealDTO> callback);

    void getAllFavorites(String userId);

    Single<CategoriesResponse> getAllCategories();

    Single<IngredientsResponse> getAllIngredients();

    Single<SimpleAreasResponse> getAllAreas();

    Single<SimpleMealsResponse> getAllMealsByIngredient(String ingredient);

    Single<SimpleMealsResponse> getAllMealsByCategory(String category);

    Single<SimpleMealsResponse> getAllMealsByArea(String area);
}

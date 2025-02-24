package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote;

import io.github.boodyahmedhamdy.mealano.data.network.dto.CategoriesResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.IngredientsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.SimpleAreasResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.SimpleCategoriesResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.SimpleMealsResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsApiService {
    @GET("random.php")
    Single<DetailedMealsResponse> getRandomMeal();

    @GET("search.php?s=")
    Single<DetailedMealsResponse> getAllMeals();

    @GET("search.php")
    Single<DetailedMealsResponse> getMealsByName(@Query("s") String name);

    @GET("search.php")
    Single<DetailedMealsResponse> getMealsByFirstCharacter(@Query("f") Character character);

    @GET("categories.php")
    Single<CategoriesResponse> getAllCategories();

    @GET("list.php?c=list")
    Single<SimpleCategoriesResponse> getAllCategoriesSimple();

    @GET("list.php?a=list")
    Single<SimpleAreasResponse> getAllAreasSimple();

    @GET("list.php?i=list")
    Single<IngredientsResponse> getAllIngredients();

    @GET("filter.php")
    Single<SimpleMealsResponse> getMealsByMainIngredient(@Query("i") String mainIngredient);

    @GET("filter.php")
    Single<SimpleMealsResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<SimpleMealsResponse> getMealsByArea(@Query("a") String area);


    @GET("lookup.php")
    Single<DetailedMealsResponse> getMealById(@Query("i") String id);
}

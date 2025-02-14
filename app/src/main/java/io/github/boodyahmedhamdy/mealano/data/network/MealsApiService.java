package io.github.boodyahmedhamdy.mealano.data.network;

import io.github.boodyahmedhamdy.mealano.data.network.dto.CategoriesResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.IngredientsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.SimpleAreasResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.SimpleCategoriesResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dto.SimpleMealsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsApiService {
    @GET("random.php")
    Call<DetailedMealsResponse> getRandomMeal();

    @GET("search.php?s=")
    Call<DetailedMealsResponse> getAllMeals();
    @GET("search.php")
    Call<DetailedMealsResponse> getMealsByName(@Query("s") String name);

    @GET("search.php")
    Call<DetailedMealsResponse> getMealsByFirstCharacter(@Query("f") Character character);

    @GET("categories.php")
    Call<CategoriesResponse> getAllCategories();

    @GET("list.php?c=list")
    Call<SimpleCategoriesResponse> getAllCategoriesSimple(); // TODO: test this

    @GET("list.php?a=list")
    Call<SimpleAreasResponse> getAllAreasSimple(); // TODO: test this

    @GET("list.php?i=list")
    Call<IngredientsResponse> getAllIngredients(); // TODO: test this

    @GET("filter.php")
    Call<SimpleMealsResponse> getMealsByMainIngredient(@Query("i") String mainIngredient); // TODO: test this

    @GET("filter.php")
    Call<SimpleMealsResponse> getMealsByCategory(@Query("c") String category); // TODO: test this

    @GET("filter.php")
    Call<SimpleMealsResponse> getMealsByArea(@Query("a") String area); // TODO: test this


    @GET("lookup.php")
    Call<DetailedMealsResponse> getMealById(@Query("i") Integer id);
}

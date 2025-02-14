package io.github.boodyahmedhamdy.mealano.data.network;

import io.github.boodyahmedhamdy.mealano.data.network.dto.MealsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsApiService {
    @GET("random.php")
    Call<MealsResponse> getRandomMeal();

    Call<MealsResponse> getAllMeals();

    @GET("lookup.php")
    Call<MealsResponse> getMealById( @Query("i") Integer id);
}

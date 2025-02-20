package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsApi {

    private static MealsApiService mealsApiService;
    private static String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "MealsApi";

    public static MealsApiService getMealsApiService() {
        if(mealsApiService == null) {
            mealsApiService = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MealsApiService.class);
            Log.i(TAG, "getMealsApiService: created new instance");
        }
        return mealsApiService;
    }
}

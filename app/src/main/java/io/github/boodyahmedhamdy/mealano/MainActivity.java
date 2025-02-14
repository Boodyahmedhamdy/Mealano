package io.github.boodyahmedhamdy.mealano;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import io.github.boodyahmedhamdy.mealano.data.network.MealsApi;
import io.github.boodyahmedhamdy.mealano.data.network.MealsApiService;
import io.github.boodyahmedhamdy.mealano.data.network.dto.MealsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MealsApiService service = MealsApi.getMealsApiService();
        Call<MealsResponse> randomMealCall = service.getRandomMeal();
        Log.i(TAG, "onCreate: Sent request randomMealCall");
        randomMealCall.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if(response.isSuccessful())
                    Log.i(TAG, "onResponse: " + response.body().getMeals().toString());
                else
                    Log.i(TAG, "onResponse: Error");
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable throwable) {
                Log.i(TAG, "onFailure: " + throwable.getMessage());
            }
        });

        Log.i(TAG, "onCreate: Sent request mealByIdCall");
        Call<MealsResponse> mealByIdCall = service.getMealById(52772);
        mealByIdCall.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if(response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().getMeals().get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable throwable) {
                Log.i(TAG, "onFailure: Error while MealById" );
            }
        });
    }
}
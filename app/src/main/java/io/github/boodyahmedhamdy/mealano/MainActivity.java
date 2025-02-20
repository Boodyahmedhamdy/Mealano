package io.github.boodyahmedhamdy.mealano;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // top app bar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.searchFragment, R.id.favoriteFragment,
                R.id.plansFragment, R.id.profileFragment, R.id.loginFragment,
                R.id.signupFragment
        ).build();
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

/*
        MealsApiService service = MealsApi.getMealsApiService();
        Call<DetailedMealsResponse> randomMealCall = service.getRandomMeal();
        Log.i(TAG, "onCreate: Sent request randomMealCall");
        randomMealCall.enqueue(new Callback<DetailedMealsResponse>() {
            @Override
            public void onResponse(Call<DetailedMealsResponse> call, Response<DetailedMealsResponse> response) {
                if(response.isSuccessful())
                    tv.setText(response.body().getMeals().toString());
                else
                    tv.setText("error while Random Meal");
            }

            @Override
            public void onFailure(Call<DetailedMealsResponse> call, Throwable throwable) {
                tv.setText(throwable.getLocalizedMessage());
            }
        });

        Log.i(TAG, "onCreate: Sent request mealByIdCall");
        Call<DetailedMealsResponse> mealByIdCall = service.getMealById(52772);
        mealByIdCall.enqueue(new Callback<DetailedMealsResponse>() {
            @Override
            public void onResponse(Call<DetailedMealsResponse> call, Response<DetailedMealsResponse> response) {
                if(response.isSuccessful()) {
                    tv.setText(response.body().getMeals().toString());
                } else {
                    tv.setText("error while Random Meal");
                }
            }

            @Override
            public void onFailure(Call<DetailedMealsResponse> call, Throwable throwable) {
                tv.setText(throwable.getLocalizedMessage());
            }
        });

        Log.i(TAG, "onCreate: sent request GetAllMeals");
        service.getAllMeals().enqueue(new Callback<DetailedMealsResponse>() {
            @Override
            public void onResponse(Call<DetailedMealsResponse> call, Response<DetailedMealsResponse> response) {
                if(response.isSuccessful()) {
                    tv.setText(response.body().getMeals().toString());
                } else {
                    tv.setText("error while Random Meal");
                }
            }

            @Override
            public void onFailure(Call<DetailedMealsResponse> call, Throwable throwable) {
                tv.setText(throwable.getLocalizedMessage());
            }
        });

        Log.i(TAG, "onCreate: sent request getMealsByName()");
        service.getMealsByName("ara").enqueue(new Callback<DetailedMealsResponse>() {
            @Override
            public void onResponse(Call<DetailedMealsResponse> call, Response<DetailedMealsResponse> response) {
                if(response.isSuccessful()) {
                    tv.setText(response.body().getMeals().toString());
                } else {
                    tv.setText("error while Random Meal");
                }
            }

            @Override
            public void onFailure(Call<DetailedMealsResponse> call, Throwable throwable) {
                tv.setText(throwable.getLocalizedMessage());
            }
        });

        Log.i(TAG, "onCreate: sent request getMealsByFirstCharacter()");
        service.getMealsByFirstCharacter('a').enqueue(new Callback<DetailedMealsResponse>() {
            @Override
            public void onResponse(Call<DetailedMealsResponse> call, Response<DetailedMealsResponse> response) {
                if(response.isSuccessful()) {
                    tv.setText(response.body().getMeals().toString());
                } else {
                    tv.setText("error while getMealsByFirstCharacter");
                }
            }

            @Override
            public void onFailure(Call<DetailedMealsResponse> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });

        Log.i(TAG, "onCreate: sent request getAllCategories()");
        service.getAllCategories().enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if(response.isSuccessful()) {
                    tv.setText(response.body().getCategories().toString());
                } else {
                    tv.setText("Error while getAllCategories()");
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });


        Log.i(TAG, "onCreate: sent request getAllCategoriesSimple()");
        service.getAllCategoriesSimple().enqueue(new Callback<SimpleCategoriesResponse>() {
            @Override
            public void onResponse(Call<SimpleCategoriesResponse> call, Response<SimpleCategoriesResponse> response) {
                tv.setText(response.body().getCategories().toString());
            }

            @Override
            public void onFailure(Call<SimpleCategoriesResponse> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });

        Log.i(TAG, "onCreate: sent request getAllAreasSimple()");
        service.getAllAreasSimple().enqueue(new Callback<SimpleAreasResponse>() {
            @Override
            public void onResponse(Call<SimpleAreasResponse> call, Response<SimpleAreasResponse> response) {
                tv.setText(response.body().getAreas().toString());
            }

            @Override
            public void onFailure(Call<SimpleAreasResponse> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });

        Log.i(TAG, "onCreate: sent request getAllIngredients()");
        service.getAllIngredients().enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                tv.setText(response.body().getIngredients().toString());
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });

        Log.i(TAG, "onCreate: sent request getMealsByMainIngredient()");
        service.getMealsByMainIngredient("Chicken").enqueue(new Callback<SimpleMealsResponse>() {
            @Override
            public void onResponse(Call<SimpleMealsResponse> call, Response<SimpleMealsResponse> response) {
                tv.setText(response.body().getMeals().toString());
            }

            @Override
            public void onFailure(Call<SimpleMealsResponse> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });

        Log.i(TAG, "onCreate: sent request getMealsByCategory()");
        service.getMealsByCategory("Seafood").enqueue(new Callback<SimpleMealsResponse>() {
            @Override
            public void onResponse(Call<SimpleMealsResponse> call, Response<SimpleMealsResponse> response) {
                tv.setText(response.body().getMeals().toString());
            }

            @Override
            public void onFailure(Call<SimpleMealsResponse> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });

        Log.i(TAG, "onCreate: sent request getMealsByArea()");
        service.getMealsByArea("Canadian").enqueue(new Callback<SimpleMealsResponse>() {
            @Override
            public void onResponse(Call<SimpleMealsResponse> call, Response<SimpleMealsResponse> response) {
                tv.setText(response.body().getMeals().toString());
            }

            @Override
            public void onFailure(Call<SimpleMealsResponse> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
*/


    }
}
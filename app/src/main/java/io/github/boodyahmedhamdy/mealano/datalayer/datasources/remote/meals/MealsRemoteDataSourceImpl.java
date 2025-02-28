package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.meals;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.boodyahmedhamdy.mealano.data.network.responses.CategoriesResponse;
import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.data.network.responses.DetailedMealsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.IngredientsResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.SimpleAreasResponse;
import io.github.boodyahmedhamdy.mealano.data.network.responses.SimpleMealsResponse;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.api.MealsApiService;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.reactivex.rxjava3.core.Single;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {

    private static final String TAG = "MealsRemoteDataSource";
    MealsApiService service;

    FirebaseDatabase firebaseDatabase;

    private MealsRemoteDataSourceImpl(MealsApiService service, FirebaseDatabase firebaseDatabase) {
        this.service = service;
        this.firebaseDatabase = firebaseDatabase;
    }


    private static MealsRemoteDataSource instance;

    public static MealsRemoteDataSource getInstance(MealsApiService service, FirebaseDatabase firebaseDatabase) {
        if(instance == null) {
            instance = new MealsRemoteDataSourceImpl(service, firebaseDatabase);
        }
        return instance;
    }

    @Override
    public Single<DetailedMealsResponse> getRandomMeal() {
        return service.getRandomMeal();

    }

    @Override
    public Single<DetailedMealsResponse> getAllMeals() {
        return service.getAllMeals();
    }

    @Override
    public Single<DetailedMealsResponse> getMealById(String mealId) {
        return service.getMealById(mealId);
    }

    @Override
    public void addToFavorite(DetailedMealDTO mealDTO, String userId, CustomCallback<DetailedMealDTO> callback) {
        DatabaseReference ref = firebaseDatabase.getReference("users").child(userId).child("favorites").child(mealDTO.getIdMeal());
        ref.setValue(mealDTO)
                .addOnSuccessListener(unused -> {
                    callback.onSuccess(mealDTO);

                    getAllFavorites(userId);
                })
                .addOnFailureListener(e -> callback.onFailure(e.getLocalizedMessage()));
    }

    @Override
    public void getAllFavorites(String userId) {
        DatabaseReference ref = firebaseDatabase.getReference("users").child(userId).child("favorites");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DetailedMealDTO meal = dataSnapshot.getValue(DetailedMealDTO.class);
                    Log.i(TAG, "onDataChange: meal:" + meal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public Single<CategoriesResponse> getAllCategories() {
        return service.getAllCategories();
    }

    @Override
    public Single<IngredientsResponse> getAllIngredients() {
        return service.getAllIngredients();
    }

    @Override
    public Single<SimpleAreasResponse> getAllAreas() {
        return service.getAllAreasSimple();
    }

    @Override
    public Single<SimpleMealsResponse> getAllMealsByIngredient(String ingredient) {
        return service.getMealsByMainIngredient(ingredient);
    }

    @Override
    public Single<SimpleMealsResponse> getAllMealsByCategory(String category) {
        return service.getMealsByCategory(category);
    }

    @Override
    public Single<SimpleMealsResponse> getAllMealsByArea(String area) {
        return service.getMealsByArea(area);
    }
}

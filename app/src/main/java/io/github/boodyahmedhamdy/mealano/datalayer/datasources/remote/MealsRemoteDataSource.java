package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealsResponse;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.github.boodyahmedhamdy.mealano.utils.network.CustomNetworkCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsRemoteDataSource {

    private static final String TAG = "MealsRemoteDataSource";
    MealsApiService service;

    FirebaseDatabase firebaseDatabase;

    private MealsRemoteDataSource(MealsApiService service, FirebaseDatabase firebaseDatabase) {
        this.service = service;
        this.firebaseDatabase = firebaseDatabase;
    }


    private static MealsRemoteDataSource instance;

    public static MealsRemoteDataSource getInstance(MealsApiService service, FirebaseDatabase firebaseDatabase) {
        if(instance == null) {
            instance = new MealsRemoteDataSource(service, firebaseDatabase);
        }
        return instance;
    }

    public void getRandomMeal(CustomNetworkCallback<DetailedMealDTO> callback) {
        service.getRandomMeal().enqueue(new Callback<DetailedMealsResponse>() {
            @Override
            public void onResponse(Call<DetailedMealsResponse> call, Response<DetailedMealsResponse> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getMeals().get(0));
                } else {
                    callback.onFailure("Failed to get Response");
                }
            }

            @Override
            public void onFailure(Call<DetailedMealsResponse> call, Throwable throwable) {
                callback.onFailure(throwable.getLocalizedMessage());
            }
        });

    }

    public void getAllMeals(CustomNetworkCallback<List<DetailedMealDTO>> callback) {
        service.getAllMeals().enqueue(new Callback<DetailedMealsResponse>() {
            @Override
            public void onResponse(Call<DetailedMealsResponse> call, Response<DetailedMealsResponse> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getMeals());
                } else {
                    callback.onFailure("Error: response isn't successful. code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DetailedMealsResponse> call, Throwable throwable) {
                callback.onFailure(throwable.getLocalizedMessage());
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
    }

    public void getMealById(Integer mealId, CustomNetworkCallback<DetailedMealDTO> callback) {
        service.getMealById(mealId).enqueue(new Callback<DetailedMealsResponse>() {
            @Override
            public void onResponse(Call<DetailedMealsResponse> call, Response<DetailedMealsResponse> response) {
                if(response.isSuccessful()) {
                    callback.onSuccess(response.body().getMeals().get(0));
                } else {
                    callback.onFailure("error from api with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DetailedMealsResponse> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
                callback.onFailure(throwable.getLocalizedMessage());
            }
        });
    }

    public void addToFavorite(DetailedMealDTO mealDTO, String userId, CustomCallback<DetailedMealDTO> callback) {
        DatabaseReference ref = firebaseDatabase.getReference("users").child(userId).child("favorites").child(mealDTO.getIdMeal());
        ref.setValue(mealDTO)
                .addOnSuccessListener(unused -> callback.onSuccess(mealDTO))
                .addOnFailureListener(e -> callback.onFailure(e.getLocalizedMessage()));
    }
}

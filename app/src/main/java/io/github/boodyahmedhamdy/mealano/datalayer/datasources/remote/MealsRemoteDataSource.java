package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealsResponse;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.github.boodyahmedhamdy.mealano.utils.network.CustomNetworkCallback;
import io.reactivex.rxjava3.core.Single;
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

    public Single<DetailedMealsResponse> getRandomMeal() {
        return service.getRandomMeal();

    }

    public Single<DetailedMealsResponse> getAllMeals() {
        return service.getAllMeals();
    }

    public Single<DetailedMealsResponse> getMealById(String mealId) {
        return service.getMealById(mealId);
    }

    public void addToFavorite(DetailedMealDTO mealDTO, String userId, CustomCallback<DetailedMealDTO> callback) {
        DatabaseReference ref = firebaseDatabase.getReference("users").child(userId).child("favorites").child(mealDTO.getIdMeal());
        ref.setValue(mealDTO)
                .addOnSuccessListener(unused -> {
                    callback.onSuccess(mealDTO);

                    getAllFavorites(userId);
                })
                .addOnFailureListener(e -> callback.onFailure(e.getLocalizedMessage()));
    }

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
}

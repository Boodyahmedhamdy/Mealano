package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.reactivex.rxjava3.core.Observable;

public class PlansRemoteDataSource {

    private static final String TAG = "PlansRemoteDataSource";

    FirebaseDatabase firebaseDatabase;

    private PlansRemoteDataSource(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    private static PlansRemoteDataSource instance;

    public static PlansRemoteDataSource getInstance(FirebaseDatabase firebaseDatabase) {
        if(instance == null) {
            instance = new PlansRemoteDataSource(firebaseDatabase);
        }
        return instance;
    }


    public Task<Void> addPlan(PlanEntity entity) {
        DatabaseReference ref = firebaseDatabase.getReference("users")
                .child(entity.getUserId()).child("plans")
                .child(entity.getDate().toString())
                .child(entity.getMealId());

        return ref.setValue(entity.getMealDTO());

    }

    public Observable<List<PlanEntity>> getAllPlans(String userId) {
        return Observable.create(emitter -> {
            DatabaseReference ref = firebaseDatabase.getReference("users")
                    .child(userId).child("plans");

            List<PlanEntity> plans = new ArrayList<>();

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dateSnapShot : snapshot.getChildren()) {
                        Long date = Long.valueOf(Objects.requireNonNull(dateSnapShot.getKey()));


                        for (DataSnapshot mealSnapShot : dateSnapShot.getChildren()) {
                            String mealId = mealSnapShot.getKey();
                            DetailedMealDTO mealDTO = mealSnapShot.getValue(DetailedMealDTO.class);

                            if (mealDTO != null) {
                                plans.add(
                                        new PlanEntity(userId, date, mealId, mealDTO)
                                );
                            }
                        }
                    }

                    emitter.onNext(plans);
                    emitter.onComplete();

                    Log.i(TAG, "user: " + userId + " have " + plans.size() + " plans available");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    emitter.onError(new Exception(error.getMessage()));
                }
            };

            // Add the listener to the reference
            ref.addListenerForSingleValueEvent(valueEventListener);

/*    public Observable<List<PlanEntity>> getAllPlans(String userId, CustomCallback<List<PlanEntity>> callback) {

        return Observable.create(emitter -> {
            DatabaseReference ref = firebaseDatabase.getReference("users")
                    .child(userId).child("plans");

            List<PlanEntity> plans = new ArrayList<>();

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dateSnapShot : snapshot.getChildren()) {
                        String strDate = dateSnapShot.getKey();

                        for (DataSnapshot mealSnapShot : dateSnapShot.getChildren()) {
                            String mealId = mealSnapShot.getKey();
                            DetailedMealDTO mealDTO = mealSnapShot.getValue(DetailedMealDTO.class);

                            if (mealDTO != null) {
                                plans.add(
                                        new PlanEntity(userId, strDate, mealId, mealDTO)
                                );
                            }
                        }
                    }

                    emitter.onNext(plans);
                    emitter.onComplete();

                    Log.i(TAG, "user: " + userId + " have " + plans.size() + " plans available");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    emitter.onError(new Exception(error.getMessage()));
                }
            };

    }*/
        });

    }

    public Task<Void> deletePlan(PlanEntity planEntity) {
        DatabaseReference ref = firebaseDatabase.getReference("users")
                .child(planEntity.getUserId())
                .child("plans")
                .child(planEntity.getDate().toString())
                .child(planEntity.getMealId());
        return ref.removeValue();
    }
}

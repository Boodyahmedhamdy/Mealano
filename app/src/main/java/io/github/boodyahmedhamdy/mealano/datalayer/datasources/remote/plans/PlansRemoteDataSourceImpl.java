package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.plans;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.reactivex.rxjava3.core.Single;

public class PlansRemoteDataSourceImpl implements PlansRemoteDataSource {

    private static final String TAG = "PlansRemoteDataSource";
    private static final String USERS = "users";
    private static final String PLANS = "plans";

    FirebaseDatabase firebaseDatabase;

    private PlansRemoteDataSourceImpl(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    private static PlansRemoteDataSource instance;

    public static PlansRemoteDataSource getInstance(FirebaseDatabase firebaseDatabase) {
        if(instance == null) {
            instance = new PlansRemoteDataSourceImpl(firebaseDatabase);
        }
        return instance;
    }


    @Override
    public Task<Void> addPlan(PlanEntity entity) {
        DatabaseReference ref = firebaseDatabase.getReference(USERS)
                .child(entity.getUserId()).child(PLANS)
                .child(entity.getDate().toString())
                .child(entity.getMealId());

        return ref.setValue(entity.getMealDTO());

    }

    @Override
    public Single<List<PlanEntity>> getAllPlans(String userId) {
        return Single.create(emitter -> {
            DatabaseReference ref = firebaseDatabase.getReference(USERS)
                    .child(userId).child(PLANS);

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
                    emitter.onSuccess(plans);

                    Log.i(TAG, "user: " + userId + " have " + plans.size() + " plans available");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    emitter.onError(new Exception(error.getMessage()));
                }
            };

            // Add the listener to the reference
            ref.addListenerForSingleValueEvent(valueEventListener);

        });

    }

    @Override
    public Task<Void> deletePlan(PlanEntity planEntity) {
        DatabaseReference ref = firebaseDatabase.getReference(USERS)
                .child(planEntity.getUserId())
                .child(PLANS)
                .child(planEntity.getDate().toString())
                .child(planEntity.getMealId());
        return ref.removeValue();
    }
}

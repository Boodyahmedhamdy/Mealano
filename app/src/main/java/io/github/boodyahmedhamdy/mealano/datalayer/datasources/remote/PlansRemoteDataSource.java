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

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;

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


    public void addPlan(PlanEntity entity) {
        DatabaseReference ref = firebaseDatabase.getReference("users")
                .child(entity.getUserId()).child("plans")
                .child(entity.getStrDate())
                .child(entity.getMealId());

        ref.setValue(entity.getMealDTO()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "onComplete:  added plan successfully to firebase" );
                } else {
                    Log.e(TAG, "onComplete: failed to add plan to firebase");
                }
            }
        });


    }

    public void getAllPlans(String userId, CustomCallback<List<PlanEntity>> callback) {
        DatabaseReference ref = firebaseDatabase.getReference("users")
                .child(userId).child("plans");

        List<PlanEntity> plans = new ArrayList<>();
        plans.add(new PlanEntity("", "", "", new DetailedMealDTO()));

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dateSnapShot : snapshot.getChildren()) {
                    String strDate = dateSnapShot.getKey();

                    for(DataSnapshot mealSnapShot: dateSnapShot.getChildren()) {
                        String mealId = mealSnapShot.getKey();
                        DetailedMealDTO mealDTO = mealSnapShot.getValue(DetailedMealDTO.class);

                        if(mealDTO != null) {
                            plans.add(
                                    new PlanEntity(userId, strDate, mealId, mealDTO)
                            );
                        }
                    }
                }
                callback.onSuccess(plans);


                Log.i(TAG, "user: " + userId + " have " + plans.size() + " plans available");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.getMessage());
            }
        });

//        return plans;


    }


}

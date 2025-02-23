package io.github.boodyahmedhamdy.mealano.datalayer.repos;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.PlansLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.PlansRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;

public class PlansRepository {

    PlansRemoteDataSource remoteDataSource;

    PlansLocalDataSource localDataSource;

    private static final String TAG = "PlansRepository";
    private static PlansRepository instance;
    private PlansRepository(PlansRemoteDataSource remoteDataSource, PlansLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static PlansRepository getInstance(PlansRemoteDataSource remoteDataSource, PlansLocalDataSource localDataSource) {
        if(instance == null) {
            instance = new PlansRepository(remoteDataSource, localDataSource);
        }
        return instance;
    }


    public void addPlan(String userId, DetailedMealDTO mealDTO, String date) {
        PlanEntity entity = new PlanEntity(userId, date, mealDTO.getIdMeal(), mealDTO);
        remoteDataSource.addPlan(entity);
//        localDataSource.insertPlan(entity);
    }

    public LiveData<List<PlanEntity>> getAllPlans(String userId) {

        remoteDataSource.getAllPlans(userId, new CustomCallback<List<PlanEntity>>() {
            @Override
            public void onSuccess(List<PlanEntity> data) {
                localDataSource.insertPlans(data);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });

        return localDataSource.getAllPlans(userId);
    }
}

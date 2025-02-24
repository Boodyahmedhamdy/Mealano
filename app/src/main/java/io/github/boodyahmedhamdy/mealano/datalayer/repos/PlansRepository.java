package io.github.boodyahmedhamdy.mealano.datalayer.repos;

import com.google.android.gms.tasks.Task;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.PlansLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.PlansRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

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


    public Task<Void> addPlanToRemote(PlanEntity planEntity) {

        return remoteDataSource.addPlan(planEntity);
//        localDataSource.insertPlan(entity);
    }

    public Flowable<List<PlanEntity>> getAllPlansFromLocal(String userId) {

//        remoteDataSource.getAllPlans(userId, new CustomCallback<List<PlanEntity>>() {
//            @Override
//            public void onSuccess(List<PlanEntity> data) {
//                localDataSource.insertPlans(data);
//            }
//
//            @Override
//            public void onFailure(String errorMessage) {
//                Log.e(TAG, errorMessage);
//            }
//        });

        return localDataSource.getAllPlans(userId);
    }

    public Observable<List<PlanEntity>> getAllPlansFromRemote(String userId) {
        return remoteDataSource.getAllPlans(userId);
    }

    public Completable insertAllPlansToLocal(List<PlanEntity> planEntities) {
        return localDataSource.insertPlans(planEntities);
    }
}

package io.github.boodyahmedhamdy.mealano.datalayer.repos;

import com.google.android.gms.tasks.Task;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.PlansLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.PlansRemoteDataSource;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;

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
    }

    public Completable addPlanToLocal(PlanEntity planEntity) {
        return localDataSource.insertPlan(planEntity);
    }

    public Flowable<List<PlanEntity>> getAllPlansFromLocal(String userId) {
        return localDataSource.getAllPlans(userId);
    }

    public Single<List<PlanEntity>> getAllPlansFromRemote(String userId) {
        return remoteDataSource.getAllPlans(userId);
    }

    public Completable insertAllPlansToLocal(List<PlanEntity> planEntities) {
        return localDataSource.insertPlans(planEntities);
    }

    public Flowable<List<PlanEntity>> getPlansFromLocalByDate(String userId, Long date) {
        return localDataSource.getPlansByDate(userId, date);
    }

    public Single<PlanEntity> getPlanFromLocalByMealId(String mealId) {
        return localDataSource.getPlanByMealId(mealId);
    }

    public Completable deletePlanFromLocal(PlanEntity planEntity) {
        return localDataSource.deletePlan(planEntity);
    }

    public Task<Void> deletePlanFromRemote(PlanEntity planEntity) {
        return remoteDataSource.deletePlan(planEntity);
    }

    public Completable deletePlansFromLocalNotIn(String userId, List<String> mealIds) {
        return localDataSource.deletePlansNotIn(userId, mealIds);
    }
}

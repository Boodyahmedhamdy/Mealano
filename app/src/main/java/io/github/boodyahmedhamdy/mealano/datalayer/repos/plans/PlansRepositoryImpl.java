package io.github.boodyahmedhamdy.mealano.datalayer.repos.plans;

import com.google.android.gms.tasks.Task;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.plans.PlansLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.plans.PlansRemoteDataSource;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class PlansRepositoryImpl implements PlansRepository {

    PlansRemoteDataSource remoteDataSource;

    PlansLocalDataSource localDataSource;

    private static final String TAG = "PlansRepository";
    private static PlansRepository instance;
    private PlansRepositoryImpl(PlansRemoteDataSource remoteDataSource, PlansLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static PlansRepository getInstance(PlansRemoteDataSource remoteDataSource, PlansLocalDataSource localDataSource) {
        if(instance == null) {
            instance = new PlansRepositoryImpl(remoteDataSource, localDataSource);
        }
        return instance;
    }


    @Override
    public Task<Void> addPlanToRemote(PlanEntity planEntity) {
        return remoteDataSource.addPlan(planEntity);
    }

    @Override
    public Completable addPlanToLocal(PlanEntity planEntity) {
        return localDataSource.insertPlan(planEntity);
    }

    @Override
    public Flowable<List<PlanEntity>> getAllPlansFromLocal(String userId) {
        return localDataSource.getAllPlans(userId);
    }

    @Override
    public Single<List<PlanEntity>> getAllPlansFromRemote(String userId) {
        return remoteDataSource.getAllPlans(userId);
    }

    @Override
    public Completable insertAllPlansToLocal(List<PlanEntity> planEntities) {
        return localDataSource.insertPlans(planEntities);
    }

    @Override
    public Flowable<List<PlanEntity>> getPlansFromLocalByDate(String userId, Long date) {
        return localDataSource.getPlansByDate(userId, date);
    }

    @Override
    public Single<PlanEntity> getPlanFromLocalByMealId(String mealId) {
        return localDataSource.getPlanByMealId(mealId);
    }

    @Override
    public Completable deletePlanFromLocal(PlanEntity planEntity) {
        return localDataSource.deletePlan(planEntity);
    }

    @Override
    public Task<Void> deletePlanFromRemote(PlanEntity planEntity) {
        return remoteDataSource.deletePlan(planEntity);
    }

    @Override
    public Completable deletePlansFromLocalNotIn(String userId, List<String> mealIds) {
        return localDataSource.deletePlansNotIn(userId, mealIds);
    }
}

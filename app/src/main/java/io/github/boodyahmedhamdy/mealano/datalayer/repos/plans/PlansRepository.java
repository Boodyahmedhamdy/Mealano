package io.github.boodyahmedhamdy.mealano.datalayer.repos.plans;

import com.google.android.gms.tasks.Task;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface PlansRepository {
    Task<Void> addPlanToRemote(PlanEntity planEntity);

    Completable addPlanToLocal(PlanEntity planEntity);

    Flowable<List<PlanEntity>> getAllPlansFromLocal(String userId);

    Single<List<PlanEntity>> getAllPlansFromRemote(String userId);

    Completable insertAllPlansToLocal(List<PlanEntity> planEntities);

    Single<PlanEntity> getPlanFromLocalByMealId(String mealId);

    Completable deletePlanFromLocal(PlanEntity planEntity);

    Task<Void> deletePlanFromRemote(PlanEntity planEntity);

    Completable deletePlansFromLocalNotIn(String userId, List<String> mealIds);
}

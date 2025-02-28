package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.plans;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface PlansLocalDataSource {
    Flowable<List<PlanEntity>> getAllPlans(String userId);

    Completable insertPlan(PlanEntity entity);

    Completable insertPlans(List<PlanEntity> entities);

    Flowable<List<PlanEntity>> getPlansByDate(String userId, Long date);

    Single<PlanEntity> getPlanByMealId(String mealId);

    Completable deletePlan(PlanEntity planEntity);

    Completable deletePlansNotIn(String userId, List<String> mealIds);
}

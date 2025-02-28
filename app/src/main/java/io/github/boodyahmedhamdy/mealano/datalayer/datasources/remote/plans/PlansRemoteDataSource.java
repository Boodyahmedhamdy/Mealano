package io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.plans;

import com.google.android.gms.tasks.Task;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.reactivex.rxjava3.core.Single;

public interface PlansRemoteDataSource {
    Task<Void> addPlan(PlanEntity entity);

    Single<List<PlanEntity>> getAllPlans(String userId);

    Task<Void> deletePlan(PlanEntity planEntity);
}

package io.github.boodyahmedhamdy.mealano.plans.contract;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;

public interface PlansView {

    void setPlans(List<PlanEntity> plans);
}

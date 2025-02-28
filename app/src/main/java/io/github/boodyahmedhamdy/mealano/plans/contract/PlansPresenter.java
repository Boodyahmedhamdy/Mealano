package io.github.boodyahmedhamdy.mealano.plans.contract;

import android.content.ContentResolver;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;

public interface PlansPresenter {
    void getAllPlans();

    void getMealById(String mealId);

    void deletePlan(PlanEntity planEntity);

    void sync();

    void sharePlanToCalendar(PlanEntity planEntity, ContentResolver contentResolver);
}

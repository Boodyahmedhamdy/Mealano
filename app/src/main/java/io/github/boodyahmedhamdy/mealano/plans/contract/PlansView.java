package io.github.boodyahmedhamdy.mealano.plans.contract;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;

public interface PlansView {

    void setPlans(List<PlanEntity> plans);

    void setErrorMessage(String errorMessage);

    void goToMealDetailsScreen(String mealId);

    void setSuccessMessage(String successMessage);

    void setIsAuthenticated(boolean isAuthenticated);
}

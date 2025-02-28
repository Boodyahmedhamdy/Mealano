package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.plans;

import android.util.Log;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos.PlansDao;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class PlansLocalDataSourceImpl implements PlansLocalDataSource {
    private static final String TAG = "PlansLocalDataSource";

    PlansDao plansDao;

    private PlansLocalDataSourceImpl(PlansDao plansDao) {
        this.plansDao = plansDao;
    }

    private static PlansLocalDataSource instance;

    public static PlansLocalDataSource getInstance(PlansDao plansDao) {
        if(instance == null) {
            instance = new PlansLocalDataSourceImpl(plansDao);
        }
        return instance;
    }

    @Override
    public Flowable<List<PlanEntity>> getAllPlans(String userId) {
        return plansDao.getAllPlans(userId);
    }

    @Override
    public Completable insertPlan(PlanEntity entity) {
        return plansDao.insertPlan(entity);
    }

    @Override
    public Completable insertPlans(List<PlanEntity> entities) {
        Log.i(TAG, "insertPlans: added successfully entities" + entities + " with size: " + entities.size());
        return plansDao.insertPlans(entities);

    }

    @Override
    public Flowable<List<PlanEntity>> getPlansByDate(String userId, Long date) {
        return plansDao.getPlansByDate(userId, date);
    }

    @Override
    public Single<PlanEntity> getPlanByMealId(String mealId) {
        return plansDao.getPlansByMealId(mealId);
    }

    @Override
    public Completable deletePlan(PlanEntity planEntity) {
        return plansDao.deletePlan(planEntity);
    }

    @Override
    public Completable deletePlansNotIn(String userId, List<String> mealIds) {
        return plansDao.deletePlansNotIn(userId, mealIds);
    }
}

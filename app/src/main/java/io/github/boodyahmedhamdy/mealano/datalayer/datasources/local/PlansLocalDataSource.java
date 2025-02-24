package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos.PlansDao;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class PlansLocalDataSource {
    private static final String TAG = "PlansLocalDataSource";

    PlansDao plansDao;

    private PlansLocalDataSource(PlansDao plansDao) {
        this.plansDao = plansDao;
    }

    private static PlansLocalDataSource instance;

    public static PlansLocalDataSource getInstance(PlansDao plansDao) {
        if(instance == null) {
            instance = new PlansLocalDataSource(plansDao);
        }
        return instance;
    }

    public Flowable<List<PlanEntity>> getAllPlans(String userId) {
        return plansDao.getAllPlans(userId);
    }

    public Completable insertPlan(PlanEntity entity) {
            return plansDao.insertPlan(entity);
    }

    public Completable insertPlans(List<PlanEntity> entities) {
        Log.i(TAG, "insertPlans: added successfully entities" + entities + " with size: " + entities.size());
        return plansDao.insertPlans(entities);

    }
}

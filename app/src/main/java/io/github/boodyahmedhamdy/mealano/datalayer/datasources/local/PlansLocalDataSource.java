package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos.PlansDao;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;

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

    public LiveData<List<PlanEntity>> getAllPlans(String userId) {
        return plansDao.getAllPlans(userId);
    }

    public void insertPlan(PlanEntity entity) {
        new Thread(() -> {
            plansDao.insertPlan(entity);
        }).start();
    }

    public void insertPlans(List<PlanEntity> entities) {
        new Thread(() -> {
            plansDao.insertPlans(entities);
            Log.i(TAG, "insertPlans: added successfully entities" + entities + " with size: " + entities.size());
        }).start();
    }
}

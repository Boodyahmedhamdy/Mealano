package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;

@Dao
public interface PlansDao {

    @Query("select * from plans where user_id = :userId")
    LiveData<List<PlanEntity>> getAllPlans(String userId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlan(PlanEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlans(List<PlanEntity> entities);
}

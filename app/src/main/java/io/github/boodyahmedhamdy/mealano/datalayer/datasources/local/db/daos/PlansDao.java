package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PlansDao {

    @Query("select * from plans where user_id = :userId order by str_date")
    Flowable<List<PlanEntity>> getAllPlans(String userId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlan(PlanEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlans(List<PlanEntity> entities);
}

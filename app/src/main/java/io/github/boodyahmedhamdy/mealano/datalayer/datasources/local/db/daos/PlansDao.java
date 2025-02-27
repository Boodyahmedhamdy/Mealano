package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PlansDao {

    @Query("select * from plans where user_id = :userId order by date")
    Flowable<List<PlanEntity>> getAllPlans(String userId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlan(PlanEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlans(List<PlanEntity> entities);

    @Query("SELECT * FROM PLANS WHERE user_id = :userId and date >= :date")
    Flowable<List<PlanEntity>> getPlansByDate(String userId, Long date);

    @Query("SELECT * FROM PLANS WHERE meal_id = :mealId")
    Single<PlanEntity> getPlansByMealId(String mealId);

    @Delete
    Completable deletePlan(PlanEntity planEntity);

    @Query("delete from plans where user_id = :userId and meal_id not in (:mealIds) ")
    Completable deletePlansNotIn(String userId, List<String> mealIds);
}

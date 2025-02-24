package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealEntity meal);

    @Query("SELECT * FROM MEALS where user_id = :userId")
    Flowable<List<MealEntity>> getAllMeals(String userId);


    @Delete
    Completable deleteMeal(MealEntity mealEntity);



}

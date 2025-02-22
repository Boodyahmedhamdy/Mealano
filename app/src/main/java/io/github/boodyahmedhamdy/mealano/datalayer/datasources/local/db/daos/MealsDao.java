package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;

@Dao
public interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealEntity meal);

    @Query("SELECT * FROM MEALS where user_id = :userId")
    LiveData<List<MealEntity>> getAllMeals(String userId);


    @Delete
    void deleteMeal(MealEntity mealEntity);



}

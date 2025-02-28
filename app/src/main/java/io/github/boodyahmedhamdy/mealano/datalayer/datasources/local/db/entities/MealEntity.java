package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;


@Entity(tableName = "meals", primaryKeys = {"meal_id", "user_id"})
public class MealEntity {

    @ColumnInfo(name = "meal_dto")
    @NonNull
    public DetailedMealDTO mealDTO;

    @NonNull
    @ColumnInfo(name = "user_id")
    public String userId;

    @NonNull
    @ColumnInfo(name = "meal_id")
    public String mealId;

    public MealEntity(@NonNull DetailedMealDTO mealDTO, @NonNull String userId, @NonNull String mealId) {
        this.mealDTO = mealDTO;
        this.userId = userId;
        this.mealId = mealId;
    }

}

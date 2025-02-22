package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;


@Entity(tableName = "meals", primaryKeys = {"meal_dto", "user_id"})
public class MealEntity {

    @ColumnInfo(name = "meal_dto")
    @NonNull
    public DetailedMealDTO mealDTO;
    @NonNull
    @ColumnInfo(name = "user_id")
    public String userId;

    public MealEntity(@NonNull DetailedMealDTO mealDTO, @NonNull String userId) {
        this.mealDTO = mealDTO;
        this.userId = userId;
    }


}

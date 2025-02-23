package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;

@Entity(tableName = "plans", primaryKeys = {"user_id", "str_date", "meal_id"})
public class PlanEntity {
    @NonNull
    @ColumnInfo(name = "user_id")
    private String userId;
    @NonNull
    @ColumnInfo(name = "str_date")
    private String strDate;
    @NonNull
    @ColumnInfo(name = "meal_id")
    private String mealId;
    private DetailedMealDTO mealDTO;


    public PlanEntity(String userId, String strDate, String mealId, DetailedMealDTO mealDTO) {
        this.userId = userId;
        this.strDate = strDate;
        this.mealId = mealId;
        this.mealDTO = mealDTO;
    }


    public PlanEntity() {
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public DetailedMealDTO getMealDTO() {
        return mealDTO;
    }

    public void setMealDTO(DetailedMealDTO mealDTO) {
        this.mealDTO = mealDTO;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }
}

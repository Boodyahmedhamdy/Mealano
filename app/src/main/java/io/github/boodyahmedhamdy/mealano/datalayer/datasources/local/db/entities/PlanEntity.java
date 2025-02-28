package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;

@Entity(tableName = "plans", primaryKeys = {"user_id", "date", "meal_id"})
public class PlanEntity {
    @NonNull
    @ColumnInfo(name = "user_id")
    private String userId;
    @NonNull
    @ColumnInfo(name = "date")
    private Long date;
    @NonNull
    @ColumnInfo(name = "meal_id")
    private String mealId;
    private DetailedMealDTO mealDTO;


    public PlanEntity(String userId, Long date, String mealId, DetailedMealDTO mealDTO) {
        this.userId = userId;
        this.date = date;
        this.mealId = mealId;
        this.mealDTO = mealDTO;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @NonNull
    public Long getDate() {
        return date;
    }

    public void setDate(@NonNull Long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PlanEntity{" +
                "userId='" + userId + '\'' +
                ", date=" + date +
                ", mealId='" + mealId + '\'' +
                ", mealDTO=" + mealDTO +
                '}';
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

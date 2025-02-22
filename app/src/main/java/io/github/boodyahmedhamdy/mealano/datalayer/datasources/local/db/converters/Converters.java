package io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;

public class Converters {
    @TypeConverter
    public String fromDetailedMealDTOToString(DetailedMealDTO mealDTO) {
        return new Gson().toJson(mealDTO);
    }

    @TypeConverter
    public DetailedMealDTO fromStringToDetailedMealDTO(String json) {
        return new Gson().fromJson(json, DetailedMealDTO.class);
    }
}

package io.github.boodyahmedhamdy.mealano.data.network.responses;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;

public class DetailedMealsResponse {

	@SerializedName("meals")
	private List<DetailedMealDTO> meals;

	public List<DetailedMealDTO> getMeals(){
		return meals;
	}

	@Override
 	public String toString(){
		return 
			"MealsResponse{" + 
			"meals = '" + meals + '\'' + 
			"}";
		}
}
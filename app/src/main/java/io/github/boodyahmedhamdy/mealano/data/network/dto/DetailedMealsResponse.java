package io.github.boodyahmedhamdy.mealano.data.network.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

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
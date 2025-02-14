package io.github.boodyahmedhamdy.mealano.data.network.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MealsResponse{

	@SerializedName("meals")
	private List<MealDTO> meals;

	public List<MealDTO> getMeals(){
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
package io.github.boodyahmedhamdy.mealano.data.network.responses;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.SimpleMealDTO;

public class SimpleMealsResponse{

	@SerializedName("meals")
	private List<SimpleMealDTO> meals;

	public List<SimpleMealDTO> getMeals(){
		return meals;
	}

	@Override
 	public String toString(){
		return 
			"SimpleMealsResponse{" + 
			"meals = '" + meals + '\'' + 
			"}";
		}
}
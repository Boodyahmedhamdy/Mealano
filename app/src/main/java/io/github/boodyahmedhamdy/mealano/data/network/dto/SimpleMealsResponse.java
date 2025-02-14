package io.github.boodyahmedhamdy.mealano.data.network.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

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
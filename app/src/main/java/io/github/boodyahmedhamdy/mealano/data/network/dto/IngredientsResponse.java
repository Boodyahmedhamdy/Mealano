package io.github.boodyahmedhamdy.mealano.data.network.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class IngredientsResponse{

	@SerializedName("meals")
	private List<DetailedIngredientDTO> ingredients;

	public List<DetailedIngredientDTO> getIngredients(){
		return ingredients;
	}

	@Override
 	public String toString(){
		return 
			"IngredientsResponse{" + 
			"meals = '" + ingredients + '\'' +
			"}";
		}
}
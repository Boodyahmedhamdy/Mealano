package io.github.boodyahmedhamdy.mealano.data.network.responses;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedIngredientDTO;

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
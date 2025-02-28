package io.github.boodyahmedhamdy.mealano.data.network.responses;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.SimpleCategoryDTO;

public class SimpleCategoriesResponse{

	@SerializedName("meals")
	private List<SimpleCategoryDTO> categories;

	public List<SimpleCategoryDTO> getCategories(){
		return categories;
	}

	@Override
 	public String toString(){
		return 
			"SimpleCategoriesResponse{" + 
			"meals = '" + categories + '\'' +
			"}";
		}
}
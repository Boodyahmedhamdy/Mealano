package io.github.boodyahmedhamdy.mealano.data.network.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

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
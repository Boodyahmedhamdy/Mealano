package io.github.boodyahmedhamdy.mealano.data.network.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoriesResponse{

	@SerializedName("categories")
	private List<DetailedCategoryDTO> categories;

	public List<DetailedCategoryDTO> getCategories(){
		return categories;
	}

	@Override
 	public String toString(){
		return 
			"CategoriesResponse{" + 
			"categories = '" + categories + '\'' + 
			"}";
		}
}
package io.github.boodyahmedhamdy.mealano.data.network.responses;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedCategoryDTO;

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
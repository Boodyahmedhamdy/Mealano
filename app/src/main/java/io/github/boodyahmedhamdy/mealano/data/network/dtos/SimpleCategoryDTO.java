package io.github.boodyahmedhamdy.mealano.data.network.dtos;

import com.google.gson.annotations.SerializedName;

public class SimpleCategoryDTO {

	@SerializedName("strCategory")
	private String strCategory;

	public String getStrCategory(){
		return strCategory;
	}

	@Override
 	public String toString(){
		return 
			"MealsItem{" + 
			"strCategory = '" + strCategory + '\'' + 
			"}";
		}
}
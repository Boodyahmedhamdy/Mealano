package io.github.boodyahmedhamdy.mealano.data.network.dtos;

import com.google.gson.annotations.SerializedName;

public class SimpleMealDTO {

	@SerializedName("strMealThumb")
	private String strMealThumb;

	@SerializedName("idMeal")
	private String idMeal;

	@SerializedName("strMeal")
	private String strMeal;

	public String getStrMealThumb(){
		return strMealThumb;
	}

	public String getIdMeal(){
		return idMeal;
	}

	public String getStrMeal(){
		return strMeal;
	}

	@Override
 	public String toString(){
		return 
			"MealsItem{" + 
			"strMealThumb = '" + strMealThumb + '\'' + 
			",idMeal = '" + idMeal + '\'' + 
			",strMeal = '" + strMeal + '\'' + 
			"}";
		}
}
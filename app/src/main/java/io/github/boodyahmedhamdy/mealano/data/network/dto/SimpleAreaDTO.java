package io.github.boodyahmedhamdy.mealano.data.network.dto;

import com.google.gson.annotations.SerializedName;

public class SimpleAreaDTO {

	@SerializedName("strArea")
	private String strArea;

	public String getStrArea(){
		return strArea;
	}

	@Override
 	public String toString(){
		return 
			"MealsItem{" + 
			"strArea = '" + strArea + '\'' + 
			"}";
		}
}
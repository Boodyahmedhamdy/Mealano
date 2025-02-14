package io.github.boodyahmedhamdy.mealano.data.network.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SimpleAreasResponse{

	@SerializedName("meals")
	private List<SimpleAreaDTO> areas;

	public List<SimpleAreaDTO> getAreas(){
		return areas;
	}

	@Override
 	public String toString(){
		return 
			"SimpleAreasResponse{" + 
			"meals = '" + areas + '\'' +
			"}";
		}
}
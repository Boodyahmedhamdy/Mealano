package io.github.boodyahmedhamdy.mealano.details.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.utils.ui.SimpleIngredientAndMeasurement;

public class IngredientsAndMeasurementsAdapter {
    List<SimpleIngredientAndMeasurement> ingredientAndMeasurements;

    public IngredientsAndMeasurementsAdapter(List<SimpleIngredientAndMeasurement> ingredientAndMeasurements) {
        this.ingredientAndMeasurements = ingredientAndMeasurements;
    }

    public static class IngredientAndMeasurementViewHolder extends RecyclerView.ViewHolder {

        public IngredientAndMeasurementViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

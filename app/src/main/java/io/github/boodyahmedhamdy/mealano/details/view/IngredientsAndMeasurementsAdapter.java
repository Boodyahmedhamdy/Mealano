package io.github.boodyahmedhamdy.mealano.details.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.databinding.SimpleIngredientAndMeasurementItemBinding;
import io.github.boodyahmedhamdy.mealano.utils.ui.SimpleIngredientAndMeasurement;

public class IngredientsAndMeasurementsAdapter extends RecyclerView.Adapter<IngredientsAndMeasurementsAdapter.IngredientAndMeasurementViewHolder> {
    List<SimpleIngredientAndMeasurement> ingredientAndMeasurements;

    public IngredientsAndMeasurementsAdapter(List<SimpleIngredientAndMeasurement> ingredientAndMeasurements) {
        this.ingredientAndMeasurements = ingredientAndMeasurements;
    }

    public void setIngredientAndMeasurements(List<SimpleIngredientAndMeasurement> ingredientAndMeasurements) {
        this.ingredientAndMeasurements = ingredientAndMeasurements;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientAndMeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientAndMeasurementViewHolder(
                SimpleIngredientAndMeasurementItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAndMeasurementViewHolder holder, int position) {
        SimpleIngredientAndMeasurement item = ingredientAndMeasurements.get(position);
        Glide.with(holder.binding.getRoot())
                .load(item.getSmallImageUrl())
                .error(R.drawable.baseline_broken_image_24)
                .placeholder(R.drawable.loading)
                .into(holder.binding.ivIngredient);
        holder.binding.tvIngredient.setText(item.getIngredient());
        holder.binding.tvMeasurement.setText(item.getMeasurement());
    }

    @Override
    public int getItemCount() {
        return ingredientAndMeasurements.size();
    }

    public static class IngredientAndMeasurementViewHolder extends RecyclerView.ViewHolder {
        SimpleIngredientAndMeasurementItemBinding binding;
        public IngredientAndMeasurementViewHolder(SimpleIngredientAndMeasurementItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

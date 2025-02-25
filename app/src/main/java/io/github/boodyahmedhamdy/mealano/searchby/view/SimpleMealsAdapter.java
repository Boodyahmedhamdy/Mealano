package io.github.boodyahmedhamdy.mealano.searchby.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.dto.SimpleMealDTO;
import io.github.boodyahmedhamdy.mealano.databinding.SimpleMealLayoutBinding;
import io.github.boodyahmedhamdy.mealano.utils.listeners.CustomClickListener;

public class SimpleMealsAdapter extends RecyclerView.Adapter<SimpleMealsAdapter.SimpleMealViewHolder> {

    List<SimpleMealDTO> meals;
    CustomClickListener<SimpleMealDTO> onCardClickListener;


    public SimpleMealsAdapter(List<SimpleMealDTO> meals, CustomClickListener<SimpleMealDTO> onCardClickListener) {
        this.meals = meals;
        this.onCardClickListener = onCardClickListener;
    }

    public void setList(List<SimpleMealDTO> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SimpleMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimpleMealViewHolder(
                SimpleMealLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleMealViewHolder holder, int position) {
        SimpleMealDTO mealDTO = meals.get(position);

        Glide.with(holder.binding.getRoot())
                .load(mealDTO.getStrMealThumb())
                .placeholder(R.drawable.loading)
                .error(R.drawable.baseline_broken_image_24)
                .into(holder.binding.ivSimpleMeal);

        holder.binding.tvSimpleMeal.setText(mealDTO.getStrMeal());

        holder.binding.getRoot().setOnClickListener(v -> {
            onCardClickListener.onClick(mealDTO);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class SimpleMealViewHolder extends RecyclerView.ViewHolder {

        SimpleMealLayoutBinding binding;
        public SimpleMealViewHolder(SimpleMealLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

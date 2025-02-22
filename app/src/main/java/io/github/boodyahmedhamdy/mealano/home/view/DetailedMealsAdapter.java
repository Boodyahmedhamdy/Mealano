package io.github.boodyahmedhamdy.mealano.home.view;


import static android.view.View.INVISIBLE;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.databinding.RandomMealCardBinding;
import io.github.boodyahmedhamdy.mealano.utils.listeners.CustomClickListener;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class DetailedMealsAdapter extends RecyclerView.Adapter<DetailedMealsAdapter.DetailedMealViewHolder> {
    private static final String TAG = "DetailedMealsAdapter";
    List<DetailedMealDTO> meals;
    CustomClickListener<Integer> onClickListener;
    CustomClickListener<DetailedMealDTO> onAddToPlanClickListener;
    CustomClickListener<DetailedMealDTO> onLoveClickListener;

    public DetailedMealsAdapter(
            List<DetailedMealDTO> meals,
            CustomClickListener<Integer> onClickListener,
            CustomClickListener<DetailedMealDTO> onAddToPlanClickListener,
            CustomClickListener<DetailedMealDTO> onLoveClickListener
    ) {
        this.meals = meals;
        this.onClickListener = onClickListener;
        this.onAddToPlanClickListener = onAddToPlanClickListener;
        this.onLoveClickListener = onLoveClickListener;
    }

    public DetailedMealsAdapter(List<DetailedMealDTO> meals, CustomClickListener<Integer> onClickListener) {
        this.meals = meals;
        this.onClickListener = onClickListener;
        Log.i(TAG, "DetailedMealsAdapter: current meals size: " + meals.size() );
    }

    public void setMeals(List<DetailedMealDTO> meals) {
        this.meals = meals;
        notifyDataSetChanged();
        Log.i(TAG, "setMeals: current meals size: " + this.meals.size() );
    }

    @NonNull
    @Override
    public DetailedMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailedMealViewHolder(
                RandomMealCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DetailedMealViewHolder holder, int position) {
        DetailedMealDTO meal = meals.get(position);
        holder.binding.getRoot().setOnClickListener(v -> {
            onClickListener.onClick(Integer.valueOf(meal.getIdMeal()));
        });
        holder.binding.tvMealTitle.setText(meal.getStrMeal());
        holder.binding.tvMealArea.setText(meal.getStrArea());
        Integer resourceId = UiUtils.flags.get(meal.getStrArea());
        if(resourceId != null) {
            holder.binding.ivMealArea.setImageResource(resourceId);
        }
        holder.binding.tvMealCategory.setText(meal.getStrCategory());
        Glide.with(holder.binding.getRoot())
                .load(meal.getStrMealThumb())
                .error(R.drawable.baseline_broken_image_24)
                .placeholder(R.drawable.loading)
                .into(holder.binding.ivMealThumbnail);
        holder.binding.tvMealTags.setVisibility(INVISIBLE);

        // favorite button
        holder.binding.btnAddToFavoriteIcon.setOnClickListener(v -> {
            Log.i(TAG, "clicked on favorite button in meal: " + meal.getStrMeal());
            onLoveClickListener.onClick(meal);
        });

        // add to plan
        holder.binding.btnMealAddToPlan.setOnClickListener(v -> {
            Log.i(TAG, "clicked on add to plan button in meal: " + meal.getStrMeal());
            onAddToPlanClickListener.onClick(meal);
        });


//        List<String> tags = meal.getStrTags() == null ? List.of() : Arrays.asList(
//                meal.getStrTags().toString().split(",")
//        );
//        Log.i(TAG, "setRandomMeal: tags: " + tags);
//        for (String tag : tags) {
//            Chip chip = new Chip(holder.binding.getRoot().getContext());
//            chip.setText(tag);
//            holder.binding.cgMealTags.addView(chip);
//        }
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: " + meals.size());
        return meals.size();
    }

    public static class DetailedMealViewHolder extends RecyclerView.ViewHolder {
        RandomMealCardBinding binding;
        public DetailedMealViewHolder(RandomMealCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

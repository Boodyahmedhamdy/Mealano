package io.github.boodyahmedhamdy.mealano.favorite.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.databinding.FavoriteMealItemBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.utils.listeners.CustomClickListener;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;

public class FavoriteMealsAdapter extends RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteMealViewHolder> {

    List<MealEntity> favoriteMeals;
    CustomClickListener<MealEntity> onDeleteBtnClickListener;
    CustomClickListener<MealEntity> onClickListener;

    public FavoriteMealsAdapter(
            List<MealEntity> favoriteMeals,
            CustomClickListener<MealEntity> onDeleteBtnClickListener,
            CustomClickListener<MealEntity> onClickListener
    ) {
        this.favoriteMeals = favoriteMeals;
        this.onDeleteBtnClickListener = onDeleteBtnClickListener;
        this.onClickListener = onClickListener;
    }

    public void setList(List<MealEntity> favoriteMeals) {
        this.favoriteMeals = favoriteMeals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new FavoriteMealViewHolder(
                FavoriteMealItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMealViewHolder holder, int position) {
        MealEntity meal = favoriteMeals.get(position);
        Glide.with(holder.binding.getRoot())
                .load(meal.mealDTO.getStrMealThumb())
                .error(R.drawable.baseline_broken_image_24)
                .placeholder(R.drawable.loading)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.binding.ivFavMealThumb);
        holder.binding.tvFavMealTitle.setText(meal.mealDTO.getStrMeal());
        holder.binding.tvFavMealCategory.setText(meal.mealDTO.getStrCategory());
        holder.binding.ivFavMealArea.setImageResource(
                UiUtils.flags.getOrDefault(meal.mealDTO.getStrArea(), R.drawable.baseline_broken_image_24)
        );
        holder.binding.tvFavMealArea.setText(meal.mealDTO.getStrArea());
        // delete button
        holder.binding.btnDeleteFavMeal.setOnClickListener(v -> {
            onDeleteBtnClickListener.onClick(meal);
        });

        // when click on the card itself
        holder.binding.getRoot().setOnClickListener(v -> {
            onClickListener.onClick(meal);
        });

    }

    @Override
    public int getItemCount() {
        return favoriteMeals.size();
    }


    public static class FavoriteMealViewHolder extends RecyclerView.ViewHolder {
        FavoriteMealItemBinding binding;
        public FavoriteMealViewHolder(FavoriteMealItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

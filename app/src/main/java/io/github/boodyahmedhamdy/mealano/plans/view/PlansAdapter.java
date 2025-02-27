package io.github.boodyahmedhamdy.mealano.plans.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.databinding.PlanItemBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.utils.listeners.CustomClickListener;
import io.github.boodyahmedhamdy.mealano.utils.ui.DatePickerUtils;

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.PlanViewHolder> {

    List<PlanEntity> plans;
    CustomClickListener<PlanEntity> onDeleteBtnClickListener;

    CustomClickListener<PlanEntity> onPlanCardClickListener;

    CustomClickListener<PlanEntity> onShareBtnClickListener;

    public PlansAdapter(List<PlanEntity> plans, CustomClickListener<PlanEntity> onDeleteBtnClickListener, CustomClickListener<PlanEntity> onPlanCardClickListener, CustomClickListener<PlanEntity> onShareBtnClickListener) {
        this.plans = plans;
        this.onDeleteBtnClickListener = onDeleteBtnClickListener;
        this.onPlanCardClickListener = onPlanCardClickListener;
        this.onShareBtnClickListener = onShareBtnClickListener;
    }

    public void setList(List<PlanEntity> plans) {
        this.plans = plans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanViewHolder(
                PlanItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        PlanEntity planEntity = plans.get(position);
        Glide.with(holder.binding.getRoot())
                .load(planEntity.getMealDTO().getStrMealThumb())
                .placeholder(R.drawable.loading)
                .error(R.drawable.baseline_broken_image_24)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.binding.ivPlan);
        holder.binding.tvPlanMealTitle.setText(planEntity.getMealDTO().getStrMeal());
        holder.binding.tvPlanDate.setText(
                DatePickerUtils.formatDateToString(planEntity.getDate())
        );

        holder.binding.getRoot().setOnClickListener(v -> {
            onPlanCardClickListener.onClick(planEntity);
        });

        holder.binding.btnDeletePlan.setOnClickListener(v -> {
            onDeleteBtnClickListener.onClick(planEntity);
        });

        holder.binding.btnSharePlanToCalendar.setOnClickListener(v -> {
            onShareBtnClickListener.onClick(planEntity);
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }


    public static class PlanViewHolder extends RecyclerView.ViewHolder {
        PlanItemBinding binding;
        public PlanViewHolder(PlanItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }




}

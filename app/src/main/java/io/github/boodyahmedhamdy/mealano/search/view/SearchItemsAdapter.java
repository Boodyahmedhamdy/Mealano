package io.github.boodyahmedhamdy.mealano.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.databinding.SearchItemBinding;
import io.github.boodyahmedhamdy.mealano.search.presenter.SearchItem;
import io.github.boodyahmedhamdy.mealano.utils.listeners.CustomClickListener;

public class SearchItemsAdapter extends RecyclerView.Adapter<SearchItemsAdapter.SearchItemViewHolder> {

    List<SearchItem> items;
    CustomClickListener<SearchItem> onItemClickListener;

    public SearchItemsAdapter(List<SearchItem> items, CustomClickListener<SearchItem> onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<SearchItem> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchItemViewHolder(
                SearchItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        SearchItem item = items.get(position);
        Glide.with(holder.binding.getRoot())
                .load(item.getImgPath())
                .error(R.drawable.baseline_broken_image_24)
                .placeholder(R.drawable.loading)
                .into(holder.binding.ivSearchItem);
        holder.binding.tvSearchItemTitle.setText(item.getTitle());

        holder.binding.getRoot().setOnClickListener(v -> {
            onItemClickListener.onClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class SearchItemViewHolder extends RecyclerView.ViewHolder {
        SearchItemBinding binding;
        public SearchItemViewHolder(SearchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

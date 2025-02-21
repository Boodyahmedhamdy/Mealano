package io.github.boodyahmedhamdy.mealano.home.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentHomeBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.MealsLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.MealsApi;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.MealsRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.home.contract.HomeView;
import io.github.boodyahmedhamdy.mealano.home.contract.OnAllMealsReceivedCallback;
import io.github.boodyahmedhamdy.mealano.home.contract.OnMealClickedCallback;
import io.github.boodyahmedhamdy.mealano.home.contract.OnMealClickedListener;
import io.github.boodyahmedhamdy.mealano.home.contract.OnRandomMealReceivedCallback;
import io.github.boodyahmedhamdy.mealano.home.presenter.HomePresenter;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class HomeFragment extends Fragment implements HomeView, OnMealClickedListener{
    private static final String TAG = "HomeFragment";
    FragmentHomeBinding binding;
    HomePresenter presenter;
    DetailedMealsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.showToolbar(requireActivity());
        UiUtils.showBottomBar(requireActivity());

        presenter = new HomePresenter(
                this, MealsRepository.getInstance(
                        new MealsLocalDataSource(),
                        new MealsRemoteDataSource(MealsApi.getMealsApiService())
                )
        );

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvAllMeals.setLayoutManager(layoutManager);
        adapter = new DetailedMealsAdapter(List.of(), this);
        binding.rvAllMeals.setAdapter(adapter);


        presenter.getRandomMeal();

        presenter.getAllMeals();

        binding.btnRefreshRandomMeal.setOnClickListener(v -> {
            presenter.getRandomMeal();
        });


    }


    @Override
    public void setRandomMeal(DetailedMealDTO detailedMealDTO) {
        binding.randomCard.getRoot().setOnClickListener(v -> {
            onClick(Integer.valueOf(detailedMealDTO.getIdMeal()));
        });
        binding.randomCard.tvMealTitle.setText(detailedMealDTO.getStrMeal());
        binding.randomCard.tvMealArea.setText(detailedMealDTO.getStrArea());
        Integer resourceId = UiUtils.flags.get(detailedMealDTO.getStrArea());
        if(resourceId != null) {
            binding.randomCard.ivMealArea.setImageResource(resourceId);
        }
        binding.randomCard.tvMealCategory.setText(detailedMealDTO.getStrCategory());

        Glide.with(requireContext())
                .load(detailedMealDTO.getStrMealThumb())
                .error(R.drawable.baseline_broken_image_24)
                .placeholder(R.drawable.loading)
                .into(binding.randomCard.ivMealThumbnail);
        List<String> tags = detailedMealDTO.getStrTags() == null ? List.of() : Arrays.asList(
                detailedMealDTO.getStrTags().toString().split(",")
        );
        Log.i(TAG, "setRandomMeal: tags: " + tags);
        tags.forEach(tag -> {
            Chip chip = new Chip(requireContext());
            chip.setText(tag);
            binding.randomCard.cgMealTags.addView(chip);
        });
    }

    @Override
    public void setAllMeals(List<DetailedMealDTO> allMeals) {
        adapter.setMeals(allMeals);
    }

    @Override
    public void goToMeal(Integer mealId) {
        Navigation.findNavController(binding.getRoot())
                .navigate(HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealId));
    }

    @Override
    public void setError(String errorMessage) {
        new AlertDialog.Builder(getContext())
                .setTitle("ERROR")
                .setMessage(errorMessage)
                .create().show();
    }

    @Override
    public void onClick(Integer mealId) {
        presenter.getMealById(mealId);
    }
}
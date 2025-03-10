package io.github.boodyahmedhamdy.mealano.details.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentMealDetailsBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.meals.MealsLocalDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.plans.PlansLocalDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.SharedPreferencesManager;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.users.UsersLocalDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.MealanoDatabase;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.api.MealsApi;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.meals.MealsRemoteDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.plans.PlansRemoteDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.users.UsersRemoteDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.meals.MealsRepositoryImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.plans.PlansRepositoryImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.users.UsersRepositoryImpl;
import io.github.boodyahmedhamdy.mealano.details.contract.MealDetailsView;
import io.github.boodyahmedhamdy.mealano.details.contract.MealDetailsPresenter;
import io.github.boodyahmedhamdy.mealano.details.presenter.MealDetailsPresenterImpl;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.ui.DatePickerUtils;
import io.github.boodyahmedhamdy.mealano.utils.ui.SimpleIngredientAndMeasurement;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;

public class MealDetailsFragment extends Fragment implements MealDetailsView {

    private static final String TAG = "MealDetailsFragment";
    FragmentMealDetailsBinding binding;
    IngredientsAndMeasurementsAdapter adapter;
    MealDetailsPresenter presenter;

    DetailedMealDTO currentMeal;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.hideBottomBar(requireActivity());

        presenter = new MealDetailsPresenterImpl(this,
                MealsRepositoryImpl.getInstance(
                        MealsLocalDataSourceImpl.getInstance(MealanoDatabase.getInstance(requireContext()).mealsDao()),
                        MealsRemoteDataSourceImpl.getInstance(MealsApi.getMealsApiService(), FirebaseDatabase.getInstance())
                ),
                UsersRepositoryImpl.getInstance(
                         UsersLocalDataSourceImpl.getInstance(SharedPreferencesManager.getInstance(requireContext()), FirebaseAuth.getInstance()),
                        UsersRemoteDataSourceImpl.getInstance(FirebaseAuth.getInstance())
                ),
                PlansRepositoryImpl.getInstance(
                        PlansRemoteDataSourceImpl.getInstance(FirebaseDatabase.getInstance()),
                        PlansLocalDataSourceImpl.getInstance(MealanoDatabase.getInstance(requireContext()).plansDao())
                ),
                NetworkMonitor.getInstance(requireContext().getSystemService(ConnectivityManager.class))
        );

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvIngredientsAndMeasurments.setLayoutManager(layoutManager);

        adapter = new IngredientsAndMeasurementsAdapter(List.of());
        binding.rvIngredientsAndMeasurments.setAdapter(adapter);

        String id = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        Log.i(TAG, "onViewCreated: id: " + id);


        binding.btnAddToFavoriteDetail.setOnClickListener(v -> {
            presenter.addMealToFavorite(currentMeal);
        });

        binding.btnAddToPlanDetail.setOnClickListener(v -> {
            DatePickerUtils.showDatePicker(requireActivity(), date -> {
                Log.i(TAG, "onViewCreated: DateSelected is " + date);
                presenter.addMealToPlans(currentMeal, date);
            });
        });


        presenter.getMealById(id);

    }

    @Override
    public void setMeal(DetailedMealDTO mealDTO) {
        currentMeal = mealDTO;
        Log.i(TAG, "setMeal: current meal is " + currentMeal);

        Log.i(TAG, "setMeal: Setting Meal started");
        Glide.with(binding.getRoot())
                .load(mealDTO.getStrMealThumb())
                .placeholder(R.drawable.loading)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.ivMealThumbnailDetail);
        binding.tvMealTitleDetail.setText(mealDTO.getStrMeal());
        binding.tvMealCategoryDetail.setText(mealDTO.getStrCategory());
        binding.tvMealAreaDetail.setText(mealDTO.getStrArea());
        binding.ivMealAreaDetail.setImageResource(
                UiUtils.flags.getOrDefault(mealDTO.getStrArea(), R.drawable.loading)
        );
        List<String> tags = mealDTO.getStrTags() == null ? List.of() : Arrays.asList(
                mealDTO.getStrTags().toString().split(",")
        );
        Log.i(TAG, "setRandomMeal: tags: " + tags);
        tags.forEach(tag -> {
            Chip chip = new Chip(requireContext());
            chip.setText(tag);
            binding.cgMealDetail.addView(chip);
        });

        binding.tvMealInstructionsDetail.setText(mealDTO.getStrInstructions());

        List<SimpleIngredientAndMeasurement> ingredientAndMeasurements =
                UiUtils.getIngredientsAndMeasurements(mealDTO);

        adapter.setIngredientAndMeasurements(ingredientAndMeasurements);

        String youtubeUrl = UiUtils.transformYoutubeUrl(mealDTO.getStrYoutube());
        if(!youtubeUrl.isEmpty() || !youtubeUrl.equals(mealDTO.getStrYoutube())) {
            binding.wvMealVideo.loadData(
                    UiUtils.getEmbedYoutubeHtml(youtubeUrl), "text/html", "utf-8"
            );
            binding.wvMealVideo.getSettings().setJavaScriptEnabled(true);
            binding.wvMealVideo.setWebChromeClient(new WebChromeClient());
            Log.i(TAG, "setMeal: youtube video must be loaded");
        }

        Log.i(TAG, "setMeal: Setting Meal finished");


    }

    @Override
    public void setErrorMessage(String errorMessage) {
        new AlertDialog.Builder(requireContext())
                .setTitle("ERROR")
                .setMessage(errorMessage)
                .create().show();
    }

    @Override
    public void setSuccessMessage(String successMessage) {
        UiUtils.showSuccessSnackBar(binding.getRoot(), successMessage);
    }

    @Override
    public void setIsLoading(Boolean isLoading) {
        if(isLoading) {
            binding.getRoot().setAlpha(0.5f);
            binding.pbDetailsScreen.setVisibility(VISIBLE);
            binding.pbDetailsScreen.setAlpha(1);
            Log.i(TAG, "setIsLoading: isLoading: " + isLoading);
        } else {
            binding.getRoot().setAlpha(1);
            binding.pbDetailsScreen.setVisibility(INVISIBLE);
            Log.i(TAG, "setIsLoading: isLoading: " + isLoading);
        }
    }

    @Override
    public void setIsOnline(Boolean isOnline) {
        if(isOnline) {

            binding.ivMealThumbnailDetail.setVisibility(VISIBLE);
            binding.tvMealTitleDetail.setVisibility(VISIBLE);

            binding.textView21.setVisibility(VISIBLE);
            binding.textView22.setVisibility(VISIBLE);
            binding.textView24.setVisibility(VISIBLE);
            binding.textView26.setVisibility(VISIBLE);
            binding.textView27.setVisibility(VISIBLE);

            binding.btnAddToFavoriteDetail.setVisibility(VISIBLE);
            binding.btnAddToPlanDetail.setVisibility(VISIBLE);

            binding.noWifiDetails.getRoot().setVisibility(INVISIBLE);

        } else {
            binding.ivMealThumbnailDetail.setVisibility(INVISIBLE);
            binding.tvMealTitleDetail.setVisibility(INVISIBLE);

            binding.textView21.setVisibility(INVISIBLE);
            binding.textView22.setVisibility(INVISIBLE);
            binding.textView24.setVisibility(INVISIBLE);
            binding.textView26.setVisibility(INVISIBLE);
            binding.textView27.setVisibility(INVISIBLE);

            binding.btnAddToFavoriteDetail.setVisibility(INVISIBLE);
            binding.btnAddToPlanDetail.setVisibility(INVISIBLE);

            binding.noWifiDetails.getRoot().setVisibility(VISIBLE);

        }

    }

    @Override
    public void setSuccessfullyAddedToFavorite(DetailedMealDTO mealDTO) {
        UiUtils.showSuccessSnackBar(
                binding.getRoot(), "added " + mealDTO.getStrMeal() + " Successfully to Favorite"
        );
        binding.btnAddToFavoriteDetail.setText("Saved to Favorite");
        binding.btnAddToFavoriteDetail.setEnabled(false);
    }


}
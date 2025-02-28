package io.github.boodyahmedhamdy.mealano.home.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.net.ConnectivityManager;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentHomeBinding;
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
import io.github.boodyahmedhamdy.mealano.home.contract.HomeView;
import io.github.boodyahmedhamdy.mealano.home.contract.HomePresenter;
import io.github.boodyahmedhamdy.mealano.home.presenter.HomePresenterImpl;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.ui.DatePickerUtils;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class HomeFragment extends Fragment implements HomeView{
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

        presenter = new HomePresenterImpl(
                this, MealsRepositoryImpl.getInstance(
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
                NetworkMonitor.getInstance(
                        (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE)
                )
        );

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvAllMeals.setLayoutManager(layoutManager);
        adapter = new DetailedMealsAdapter(
                List.of(),
                mealId -> {presenter.getMealById(mealId);},
                mealDTO -> {
                    DatePickerUtils.showDatePicker(requireActivity(), date -> {
                        presenter.addMealToPlans(
                                mealDTO, date
                        );
                    });
                },
                mealDTO -> { /*presenter.addMealToFavorite(mealDTO);*/ }
        );
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
            presenter.getMealById(detailedMealDTO.getIdMeal());
        });
        binding.randomCard.tvMealTitle.setText(detailedMealDTO.getStrMeal());
        binding.randomCard.tvMealArea.setText(detailedMealDTO.getStrArea());
        Integer resourceId = UiUtils.flags.get(detailedMealDTO.getStrArea());
        if(resourceId != null) {
            binding.randomCard.ivMealArea.setImageResource(resourceId);
        }
        binding.randomCard.tvMealCategory.setText(detailedMealDTO.getStrCategory());

        Glide.with(binding.getRoot())
                .load(detailedMealDTO.getStrMealThumb())
                .error(R.drawable.baseline_broken_image_24)
                .placeholder(R.drawable.loading)
                .into(binding.randomCard.ivMealThumbnail);

        binding.randomCard.cgMealTags.removeViews(0, binding.randomCard.cgMealTags.getChildCount());
        List<String> tags = detailedMealDTO.getStrTags() == null ? List.of() : Arrays.asList(
                detailedMealDTO.getStrTags().toString().split(",")
        );
        Log.i(TAG, "setRandomMeal: tags: " + tags);
        tags.forEach(tag -> {
            Chip chip = new Chip(binding.getRoot().getContext());
            chip.setText(tag);
            binding.randomCard.cgMealTags.addView(chip);
        });

        // add to plan
        binding.randomCard.btnMealAddToPlan.setOnClickListener(v -> {
            Log.i(TAG, "clicked on add to plan button onRandome in meal: " + detailedMealDTO.getStrMeal());
            DatePickerUtils.showDatePicker(requireActivity(), date -> {
                        presenter.addMealToPlans(
                                detailedMealDTO, date
                        );
                    });
        });

    }

    @Override
    public void setAllMeals(List<DetailedMealDTO> allMeals) {
        adapter.setMeals(allMeals);
    }

    @Override
    public void goToMeal(String mealId) {
        Navigation.findNavController(binding.getRoot())
                .navigate(HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealId));
    }

    @Override
    public void setError(String errorMessage) {
        UiUtils.showErrorSnackBar(binding.getRoot(), errorMessage);
    }

    @Override
    public void setIsLoading(Boolean isLoading) {
        if(isLoading) {
            binding.getRoot().setAlpha(0.5f);
            binding.pbHomeScreen.setVisibility(VISIBLE);
            binding.pbHomeScreen.setAlpha(1);
            Log.i(TAG, "setIsLoading: isLoading: " + isLoading);
        } else {
            binding.getRoot().setAlpha(1);
            binding.pbHomeScreen.setVisibility(INVISIBLE);
            Log.i(TAG, "setIsLoading: isLoading: " + isLoading);
        }
    }

    @Override
    public void setIsOnline(Boolean isOnline) {
        Log.i(TAG, "setIsOnline: isonline: " + isOnline);
        if(isOnline) {
            binding.randomCard.getRoot().setVisibility(VISIBLE);
            binding.rvAllMeals.setVisibility(VISIBLE);
            binding.textView15.setVisibility(VISIBLE);
            binding.textView18.setVisibility(VISIBLE);
            binding.btnRefreshRandomMeal.setVisibility(VISIBLE);
            binding.noWifiCardHome.getRoot().setVisibility(INVISIBLE);
        } else {
            binding.randomCard.getRoot().setVisibility(INVISIBLE);
            binding.rvAllMeals.setVisibility(INVISIBLE);
            binding.textView15.setVisibility(INVISIBLE);
            binding.textView18.setVisibility(INVISIBLE);
            binding.btnRefreshRandomMeal.setVisibility(INVISIBLE);
            binding.noWifiCardHome.getRoot().setVisibility(VISIBLE);
        }
    }

    @Override
    public void setSuccessMessage(String successMessage) {

        UiUtils.showSuccessSnackBar(binding.getRoot(), successMessage);
    }

}
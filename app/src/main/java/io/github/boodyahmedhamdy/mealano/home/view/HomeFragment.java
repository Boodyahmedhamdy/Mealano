package io.github.boodyahmedhamdy.mealano.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentHomeBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.MealsLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.MealsApi;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.MealsRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.home.contract.HomeView;
import io.github.boodyahmedhamdy.mealano.home.contract.OnRandomMealReceivedCallback;
import io.github.boodyahmedhamdy.mealano.home.presenter.HomePresenter;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class HomeFragment extends Fragment implements HomeView {
    FragmentHomeBinding binding;

    HomePresenter presenter;
    private static final String TAG = "HomeFragment";




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

        presenter.getRandomMeal(new OnRandomMealReceivedCallback() {
            @Override
            public void onSuccess(DetailedMealDTO detailedMealDTO) {
                setRandomMeal(detailedMealDTO);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "onFailure: " + errorMessage);
            }
        });


    }


    @Override
    public void setRandomMeal(DetailedMealDTO detailedMealDTO) {
        binding.randomCard.tvMealTitle.setText(detailedMealDTO.getStrMeal());
        binding.randomCard.tvMealArea.setText(detailedMealDTO.getStrArea());
        binding.randomCard.tvMealCategory.setText(detailedMealDTO.getStrCategory());
    }

    @Override
    public void setAllMeals(List<DetailedMealDTO> allMeals) {

    }
}
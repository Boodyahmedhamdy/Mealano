package io.github.boodyahmedhamdy.mealano.plans.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentPlansBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.PlansLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.SharedPreferencesManager;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.UsersLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.MealanoDatabase;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.PlansRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.UsersRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.PlansRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.favorite.view.FavoriteFragmentDirections;
import io.github.boodyahmedhamdy.mealano.plans.contract.PlansView;
import io.github.boodyahmedhamdy.mealano.plans.presenter.PlansPresenter;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class PlansFragment extends Fragment implements PlansView {

    private static final String TAG = "PlansFragment";
    FragmentPlansBinding binding;
    PlansPresenter presenter;
    PlansAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlansBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.showToolbar(requireActivity());
        UiUtils.showBottomBar(requireActivity());

        presenter = new PlansPresenter(this,
                PlansRepository.getInstance(
                        PlansRemoteDataSource.getInstance(FirebaseDatabase.getInstance()),
                        PlansLocalDataSource.getInstance(MealanoDatabase.getInstance(requireContext()).plansDao())
                ),
                UsersRepository.getInstance(
                        UsersLocalDataSource.getInstance(
                                SharedPreferencesManager.getInstance(requireContext()),
                                FirebaseAuth.getInstance()
                        ),
                        UsersRemoteDataSource.getInstance(FirebaseAuth.getInstance()))
                );

        adapter = new PlansAdapter(
            List.of(),
            planEntity -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Warning!!")
                        .setIcon(R.drawable.baseline_error_outline_24)
                        .setMessage("you are about to Remove " + planEntity.getMealDTO().getStrMeal() + " from plans. Are you Sure??")
                        .setPositiveButton("Yes Remove it", (dialog, which) -> {
                            presenter.deletePlan(planEntity);
                        })
                        .setNegativeButton("No Keep it", (dialog, which) -> {})
                        .create().show();
            },
            planEntity -> {
                presenter.getMealById(planEntity.getMealId());
            }
        );

        binding.rvPlans.setAdapter(adapter);

        presenter.syncPlans();
        presenter.getAllPlans();

    }



    @Override
    public void setPlans(List<PlanEntity> plans) {

        adapter.setList(plans);

    }

    @Override
    public void setErrorMessage(String errorMessage) {

    }

    @Override
    public void goToMealDetailsScreen(String mealId) {
        Navigation.findNavController(binding.getRoot()).navigate(
                PlansFragmentDirections.actionPlansFragmentToMealDetailsFragment(mealId)
        );
    }

    @Override
    public void setSyncMessage(String syncMessage) {
        Snackbar.make(binding.getRoot(), syncMessage, Snackbar.LENGTH_LONG).show();
    }
}
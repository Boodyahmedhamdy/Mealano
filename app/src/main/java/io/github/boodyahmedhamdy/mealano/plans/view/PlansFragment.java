package io.github.boodyahmedhamdy.mealano.plans.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import io.github.boodyahmedhamdy.mealano.plans.contract.PlansView;
import io.github.boodyahmedhamdy.mealano.plans.presenter.PlansPresenter;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class PlansFragment extends Fragment implements PlansView {

    private static final String TAG = "PlansFragment";
    FragmentPlansBinding binding;
    PlansPresenter presenter;



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

        presenter.getAllPlans().observe(getViewLifecycleOwner(), planEntities -> {
            setPlans(planEntities);
        });



    }



    @Override
    public void setPlans(List<PlanEntity> plans) {
        binding.tvTemp.setText(
                "you have " + plans.size() + " plans "
        );
    }
}
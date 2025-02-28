package io.github.boodyahmedhamdy.mealano.plans.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.net.ConnectivityManager;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentPlansBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.plans.PlansLocalDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.SharedPreferencesManager;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.users.UsersLocalDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.MealanoDatabase;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.plans.PlansRemoteDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.users.UsersRemoteDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.plans.PlansRepositoryImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.users.UsersRepositoryImpl;
import io.github.boodyahmedhamdy.mealano.plans.contract.PlansView;
import io.github.boodyahmedhamdy.mealano.plans.presenter.PlansPresenter;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
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
                PlansRepositoryImpl.getInstance(
                        PlansRemoteDataSourceImpl.getInstance(FirebaseDatabase.getInstance()),
                        PlansLocalDataSourceImpl.getInstance(MealanoDatabase.getInstance(requireContext()).plansDao())
                ),
                UsersRepositoryImpl.getInstance(
                        UsersLocalDataSourceImpl.getInstance(
                                SharedPreferencesManager.getInstance(requireContext()),
                                FirebaseAuth.getInstance()
                        ),
                        UsersRemoteDataSourceImpl.getInstance(FirebaseAuth.getInstance())),
                NetworkMonitor.getInstance(requireContext().getSystemService(ConnectivityManager.class))
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
            },
            planEntity -> {
                presenter.sharePlanToCalendar(planEntity);
            }
        );

        binding.rvPlans.setAdapter(adapter);

        presenter.syncV2();
        presenter.getAllPlans();


    }

    @Override
    public void setPlans(List<PlanEntity> plans) {
        if(plans.isEmpty()) {
            binding.tvNoPlansYet.setVisibility(VISIBLE);
        } else {
            binding.tvNoPlansYet.setVisibility(INVISIBLE);
        }
        adapter.setList(plans);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        Log.e(TAG, "setErrorMessage:" + errorMessage);
        UiUtils.showErrorSnackBar(binding.getRoot(), errorMessage);
    }

    @Override
    public void goToMealDetailsScreen(String mealId) {
        Navigation.findNavController(binding.getRoot()).navigate(
                PlansFragmentDirections.actionPlansFragmentToMealDetailsFragment(mealId)
        );
    }

    @Override
    public void setSuccessMessage(String successMessage) {
        UiUtils.showSuccessSnackBar(binding.getRoot(), successMessage);
    }

    @Override
    public void setIsAuthenticated(boolean isAuthenticated) {
        if (isAuthenticated) {

            binding.lockLayoutPlans.getRoot().setVisibility(INVISIBLE);
            binding.calendarView.setVisibility(VISIBLE);
            binding.rvPlans.setVisibility(VISIBLE);
            binding.tvNoPlansYet.setVisibility(VISIBLE);

        } else {
            binding.lockLayoutPlans.getRoot().setVisibility(VISIBLE);
            binding.calendarView.setVisibility(INVISIBLE);
            binding.rvPlans.setVisibility(INVISIBLE);
            binding.tvNoPlansYet.setVisibility(INVISIBLE);
        }
    }

//    private void requestCalendarPermissions() {
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
}
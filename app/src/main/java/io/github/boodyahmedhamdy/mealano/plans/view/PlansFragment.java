package io.github.boodyahmedhamdy.mealano.plans.view;

import android.content.Context;
import android.icu.util.Calendar;
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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

        // Get today's date
        Calendar calendar = Calendar.getInstance();
        binding.calendarView.setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        binding.calendarView.setMaxDate(calendar.getTimeInMillis());
        binding.calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String selectedDate = sdf.format(new Date(calendar.getTimeInMillis()));

            Toast.makeText(binding.getRoot().getContext(), selectedDate, Toast.LENGTH_SHORT).show();
        });


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
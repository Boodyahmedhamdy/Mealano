package io.github.boodyahmedhamdy.mealano.searchby.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.dtos.SimpleMealDTO;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentSearchByBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.meals.MealsLocalDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.MealanoDatabase;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.api.MealsApi;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.meals.MealsRemoteDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.meals.MealsRepositoryImpl;
import io.github.boodyahmedhamdy.mealano.searchby.contract.SearchByView;
import io.github.boodyahmedhamdy.mealano.searchby.contract.SearchByPresenter;
import io.github.boodyahmedhamdy.mealano.searchby.presenter.SearchByPresenterImpl;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;

public class SearchByFragment extends Fragment implements SearchByView {
    private static final String TAG = "SearchByFragment";
    FragmentSearchByBinding binding;
    SimpleMealsAdapter adapter;
    SearchByPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchByBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.hideBottomBar(requireActivity());

        String key = SearchByFragmentArgs.fromBundle(getArguments()).getSearchByKey();
        String value = SearchByFragmentArgs.fromBundle(getArguments()).getSearchByValue();

        presenter = new SearchByPresenterImpl(
                this,
                MealsRepositoryImpl.getInstance(
                        MealsLocalDataSourceImpl.getInstance(MealanoDatabase.getInstance(requireContext()).mealsDao()),
                        MealsRemoteDataSourceImpl.getInstance(MealsApi.getMealsApiService(), FirebaseDatabase.getInstance())
                ),
                NetworkMonitor.getInstance(requireContext().getSystemService(ConnectivityManager.class))
        );

        adapter = new SimpleMealsAdapter(List.of(), mealDTO -> {
            Navigation.findNavController(binding.getRoot()).navigate(
                    SearchByFragmentDirections.actionSearchByFragmentToMealDetailsFragment(mealDTO.getIdMeal())
            );
        });

        binding.rvSimpleMeals.setAdapter(adapter);


        binding.tietSearchBy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "current text is: " + s);
                presenter.searchBy(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        presenter.getAll(key, value);


    }

    @Override
    public void setIsOnline(boolean isOnline) {
        if(isOnline) {
            binding.noWifiSearchBy.getRoot().setVisibility(INVISIBLE);
            binding.tietSearchBy.setVisibility(VISIBLE);
            binding.textInputLayout2.setVisibility(VISIBLE);
            binding.rvSimpleMeals.setVisibility(VISIBLE);
        } else {
            binding.noWifiSearchBy.getRoot().setVisibility(VISIBLE);
            binding.textInputLayout2.setVisibility(INVISIBLE);
            binding.tietSearchBy.setVisibility(INVISIBLE);
            binding.rvSimpleMeals.setVisibility(INVISIBLE);
        }

    }

    @Override
    public void setErrorMessage(String errorMessage) {
        Snackbar.make(binding.getRoot(), errorMessage, Snackbar.LENGTH_LONG)
                .setBackgroundTint(getResources().getColor(R.color.md_theme_error))
                .setTextColor(getResources().getColor(R.color.md_theme_onError))
                .setAction("Dismiss", v -> {
                    Log.i(TAG, "onViewCreated: clicked on action");
                })
                .show();
    }

    @Override
    public void setList(List<SimpleMealDTO> simpleMealDTOS) {
        adapter.setList(simpleMealDTOS);
    }

    @Override
    public void setIsLoading(boolean isLoading) {
        if(isLoading) {
            binding.rvSimpleMeals.setAlpha(0.5f);
            binding.pbSearchBy.setVisibility(VISIBLE);
        } else {
            binding.rvSimpleMeals.setAlpha(1);
            binding.pbSearchBy.setVisibility(INVISIBLE);
        }

    }
}
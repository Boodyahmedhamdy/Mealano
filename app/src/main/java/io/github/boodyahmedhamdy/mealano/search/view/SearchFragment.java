package io.github.boodyahmedhamdy.mealano.search.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.constants.SearchConstants;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentSearchBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.meals.MealsLocalDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.MealanoDatabase;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.api.MealsApi;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.meals.MealsRemoteDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.meals.MealsRepositoryImpl;
import io.github.boodyahmedhamdy.mealano.search.contract.SearchView;
import io.github.boodyahmedhamdy.mealano.search.contract.SearchPresenter;
import io.github.boodyahmedhamdy.mealano.search.presenter.SearchPresenterImpl;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;

public class SearchFragment extends Fragment implements SearchView {

    private static final String TAG = "SearchFragment";
    FragmentSearchBinding binding;

    SearchPresenter presenter;
    SearchItemsAdapter adapter;

    String searchBy;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBy = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.showToolbar(requireActivity());
        UiUtils.showBottomBar(requireActivity());

        presenter = new SearchPresenterImpl(
                this,
                MealsRepositoryImpl.getInstance(
                        MealsLocalDataSourceImpl.getInstance(MealanoDatabase.getInstance(requireContext()).mealsDao()),
                        MealsRemoteDataSourceImpl.getInstance(MealsApi.getMealsApiService(), FirebaseDatabase.getInstance())
                ),
                NetworkMonitor.getInstance(requireContext().getSystemService(ConnectivityManager.class))
        );


        for(int i = 0 ; i < binding.chipGroup.getChildCount() ; i++) {
            Chip chip = (Chip) binding.chipGroup.getChildAt(i);
            chip.setChecked(false);
        }

        binding.chipCategory.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                presenter.getAllCategories();
                searchBy = SearchConstants.CATEGORY;
            }
        });

        binding.chipArea.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                presenter.getAllAreas();
                searchBy = SearchConstants.AREA;
            }
        });

        binding.chipIngredient.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                presenter.getAllIngredients();
                searchBy = SearchConstants.INGREDIENT;
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvSearchItems.setLayoutManager(gridLayoutManager);

        adapter = new SearchItemsAdapter(List.of(), item -> {
            Log.i(TAG, "clicked on " + item);
            Navigation.findNavController(binding.getRoot()).navigate(
                    SearchFragmentDirections.actionSearchFragmentToSearchByFragment(searchBy, item.getTitle())
            );
        });
        binding.rvSearchItems.setAdapter(adapter);


        binding.tietSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "text changed to " + s);
                presenter.searchFor(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @Override
    public void setIsOnline(boolean isOnline) {
        Log.d(TAG, "setIsOnline() called with: isOnline = [" + isOnline + "]");
        if(isOnline) {
            binding.tilSearch.setVisibility(VISIBLE);
            binding.chipGroup.setVisibility(VISIBLE);
            binding.rvSearchItems.setVisibility(VISIBLE);
            binding.noWifiLayoutSearch.getRoot().setVisibility(INVISIBLE);
        } else {
            binding.tilSearch.setVisibility(INVISIBLE);
            binding.chipGroup.setVisibility(INVISIBLE);
            binding.rvSearchItems.setVisibility(INVISIBLE);
            binding.noWifiLayoutSearch.getRoot().setVisibility(VISIBLE);
            Snackbar.make(binding.getRoot(), "Network Lost, you are Offline", Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        Snackbar.make(binding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setList(List<SearchItem> searchItems) {
        Log.d(TAG, "setList() called with: searchItems = [" + searchItems + "]");
        adapter.setList(searchItems);
    }
}
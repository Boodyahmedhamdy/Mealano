package io.github.boodyahmedhamdy.mealano.favorite.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

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
import io.github.boodyahmedhamdy.mealano.databinding.FragmentFavoriteBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.meals.MealsLocalDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.SharedPreferencesManager;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.users.UsersLocalDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.MealanoDatabase;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.api.MealsApi;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.meals.MealsRemoteDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.users.UsersRemoteDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.meals.MealsRepositoryImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.users.UsersRepositoryImpl;
import io.github.boodyahmedhamdy.mealano.favorite.contract.FavoriteView;
import io.github.boodyahmedhamdy.mealano.favorite.contract.FavoritePresenter;
import io.github.boodyahmedhamdy.mealano.favorite.presenter.FavoritePresenterImpl;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class FavoriteFragment extends Fragment implements FavoriteView {
    private static final String TAG = "FavoriteFragment";
    FragmentFavoriteBinding binding;

    FavoritePresenter presenter;

    FavoriteMealsAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.showToolbar(requireActivity());
        UiUtils.showBottomBar(requireActivity());

        presenter = new FavoritePresenterImpl(
                this,
                MealsRepositoryImpl.getInstance(
                        MealsLocalDataSourceImpl.getInstance(MealanoDatabase.getInstance(requireContext()).mealsDao()),
                        MealsRemoteDataSourceImpl.getInstance(MealsApi.getMealsApiService(), FirebaseDatabase.getInstance())
                ),
                UsersRepositoryImpl.getInstance(
                        UsersLocalDataSourceImpl.getInstance(SharedPreferencesManager.getInstance(requireContext()), FirebaseAuth.getInstance()),
                        UsersRemoteDataSourceImpl.getInstance(FirebaseAuth.getInstance())
                )
        );

        adapter = new FavoriteMealsAdapter(
                List.of(),
                data -> {
                    Log.i(TAG, "onClick: clicked on delete icon");
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Warning")
                            .setIcon(R.drawable.baseline_error_outline_24)
                            .setMessage("you are about to remove " + data.mealDTO.getStrMeal() + " from your favorite meals. are you sure? ")
                            .setPositiveButton("Yes Delete", (dialog, which) -> {
                                presenter.deleteFavoriteMeal(data);
                            })
                            .setNegativeButton("No Keep it", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .create()
                            .show();
                },
                data -> {
                    Log.i(TAG, "onClick: clicked on card");
                    Navigation.findNavController(binding.getRoot()).navigate(
                            FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(data.mealDTO.getIdMeal())
                    );

                }
        );

        binding.rvFavoriteMeals.setAdapter(adapter);

        presenter.getFavoriteMeals();

    }



    @Override
    public void setFavoriteMeals(List<MealEntity> meals) {
        if(meals.isEmpty()) {
            binding.tvNoFavMealsYet.setVisibility(VISIBLE);
        } else {
            binding.tvNoFavMealsYet.setVisibility(INVISIBLE);
        }
            adapter.setList(meals);
    }

    @Override
    public void goToDetailsScreen() {

    }

    @Override
    public void setErrorMessage(String errorMessage) {
        Log.i(TAG, "setErrorMessage: " + errorMessage);
    }

    @Override
    public void setIsOnline(boolean isOnline) {
        if(isOnline) {
            binding.rvFavoriteMeals.setVisibility(VISIBLE);
//            binding.noWifiLayoutFav.getRoot().setVisibility(INVISIBLE);
        } else {
            binding.rvFavoriteMeals.setVisibility(INVISIBLE);
//            binding.noWifiLayoutFav.getRoot().setVisibility(VISIBLE);
        }
    }

    @Override
    public void setIsAuthorized(boolean isAuthorized) {
        if(isAuthorized) {
            binding.rvFavoriteMeals.setVisibility(VISIBLE);
//            binding.noWifiLayoutFav.getRoot().setVisibility(INVISIBLE);
            binding.lockLayoutFavorite.getRoot().setVisibility(INVISIBLE);
        } else {
            binding.rvFavoriteMeals.setVisibility(INVISIBLE);
//            binding.noWifiLayoutFav.getRoot().setVisibility(INVISIBLE);
            binding.lockLayoutFavorite.getRoot().setVisibility(VISIBLE);

        }
    }
}
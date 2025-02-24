package io.github.boodyahmedhamdy.mealano.favorite.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.favorite.contract.FavoriteView;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.reactivex.rxjava3.disposables.Disposable;

public class FavoritePresenter {
    private static final String TAG = "FavoritePresenter";

    FavoriteView view;
    MealsRepository mealsRepository;
    UsersRepository usersRepository;

    NetworkMonitor networkMonitor;


    public FavoritePresenter(FavoriteView view, MealsRepository mealsRepository, UsersRepository usersRepository, NetworkMonitor networkMonitor) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.usersRepository = usersRepository;
        this.networkMonitor = networkMonitor;
    }

    public void getFavoriteMeals() {
        if(usersRepository.getCurrentUser() != null) {
            view.setIsAuthorized(true);

            Disposable dis =  mealsRepository.getFavoriteMeals(
                    usersRepository.getCurrentUser().getUid()
            ).compose(new OnBackgroundTransformer<>())
                    .subscribe(mealEntities -> {
                        view.setFavoriteMeals(mealEntities);
                    }, throwable -> {
                        view.setErrorMessage(throwable.getLocalizedMessage());
                    });
        } else {
            view.setIsAuthorized(false);
        }

    }

    public void deleteFavoriteMeal(MealEntity meal) {
        if(networkMonitor.isConnected()) {
            view.setIsOnline(true);
            Disposable dis =  mealsRepository.deleteFavoriteMeal(meal)
                    .compose(new OnBackgroundTransformer<>())
                    .subscribe(
                            () -> Log.i(TAG, "deleteFavoriteMeal: deleted " + meal.mealDTO.getStrMeal() + " successfully"),
                            throwable -> Log.e(TAG, "deleteFavoriteMeal: ", throwable)
                    );
        } else {
            view.setIsOnline(false);
        }


    }
}

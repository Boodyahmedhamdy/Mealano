package io.github.boodyahmedhamdy.mealano.favorite.presenter;

import android.util.Log;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.meals.MealsRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.users.UsersRepository;
import io.github.boodyahmedhamdy.mealano.favorite.contract.FavoritePresenter;
import io.github.boodyahmedhamdy.mealano.favorite.contract.FavoriteView;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.reactivex.rxjava3.disposables.Disposable;

public class FavoritePresenterImpl implements FavoritePresenter {
    private static final String TAG = "FavoritePresenter";

    FavoriteView view;
    MealsRepository mealsRepository;
    UsersRepository usersRepository;




    public FavoritePresenterImpl(FavoriteView view, MealsRepository mealsRepository, UsersRepository usersRepository) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
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

    @Override
    public void deleteFavoriteMeal(MealEntity meal) {

        Disposable dis =  mealsRepository.deleteFavoriteMeal(meal)
                .compose(new OnBackgroundTransformer<>())
                .subscribe(
                        () -> Log.i(TAG, "deleteFavoriteMeal: deleted " + meal.mealDTO.getStrMeal() + " successfully"),
                        throwable -> Log.e(TAG, "deleteFavoriteMeal: ", throwable)
                );



    }
}

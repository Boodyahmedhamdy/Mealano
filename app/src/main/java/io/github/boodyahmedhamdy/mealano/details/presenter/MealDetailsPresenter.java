package io.github.boodyahmedhamdy.mealano.details.presenter;

import android.util.Log;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.PlansRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.details.contract.IMealDetailsPresenter;
import io.github.boodyahmedhamdy.mealano.details.contract.MealDetailsView;
import io.github.boodyahmedhamdy.mealano.utils.callbacks.CustomCallback;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.reactivex.rxjava3.disposables.Disposable;

public class MealDetailsPresenter implements IMealDetailsPresenter {

    private static final String TAG = "MealDetailsPresenter";
    MealDetailsView view;
    MealsRepository mealsRepository;

    UsersRepository usersRepository;

    PlansRepository plansRepository;

    public MealDetailsPresenter(MealDetailsView view, MealsRepository mealsRepository, UsersRepository usersRepository, PlansRepository plansRepository) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.usersRepository = usersRepository;
        this.plansRepository = plansRepository;
    }



    @Override
    public void getMealById(String mealId) {
        view.setIsLoading(true);
        Disposable disposable =  mealsRepository.getMealById(mealId)
                .compose(new OnBackgroundTransformer<>())
                .map(detailedMealsResponse -> detailedMealsResponse.getMeals().get(0))
                .subscribe(mealDTO -> {
                    view.setMeal(mealDTO);
                    view.setIsLoading(false);
                }, throwable -> {
                    view.setErrorMessage(throwable.getLocalizedMessage());
                    view.setIsLoading(false);
                });
    }

    public void addMealToFavorite(DetailedMealDTO mealDTO) {
        Log.i(TAG, "addMealToFavorite: started adding to favorite");

        // TODO: if user is authenticated and is online
        Disposable dis = mealsRepository.addMealToFavorite(
                new MealEntity(mealDTO, usersRepository.getCurrentUser().getUid(), mealDTO.getIdMeal())
            )
            .compose(new OnBackgroundTransformer<>())
            .subscribe(() -> {
                view.setSuccessfullyAddedToFavorite(mealDTO);
                Log.i(TAG, "addMealToFavorite: finished adding to favorite");
            },
                    throwable -> {
                view.setErrorMessage(throwable.getLocalizedMessage());
                Log.e(TAG, "addMealToFavorite: ", throwable);
            });
    }

    public void addMealToPlans(DetailedMealDTO currentMeal, String date) {
        plansRepository.addPlan(usersRepository.getCurrentUser().getUid(), currentMeal, date);
    }
}

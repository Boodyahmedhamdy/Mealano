package io.github.boodyahmedhamdy.mealano.home.presenter;

import android.util.Log;

import java.util.Collections;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.PlansRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.home.contract.HomeView;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.reactivex.rxjava3.disposables.Disposable;

public class HomePresenter {
    private static final String TAG = "HomePresenter";

    HomeView view;


    MealsRepository mealsRepository;
    UsersRepository usersRepository;
    PlansRepository plansRepository;
    NetworkMonitor networkMonitor;

    public HomePresenter(HomeView view, MealsRepository mealsRepository, UsersRepository usersRepository, PlansRepository plansRepository, NetworkMonitor networkMonitor) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.usersRepository = usersRepository;
        this.plansRepository = plansRepository;
        this.networkMonitor = networkMonitor;
    }

    public void getRandomMeal() {

        if(networkMonitor.isConnected()) {
            view.setIsOnline(true);
            view.setIsLoading(true);
            Disposable dis = mealsRepository.getRandomMeal()
                    .compose(new OnBackgroundTransformer<>())
                    .map(detailedMealsResponse -> detailedMealsResponse.getMeals().get(0))
                    .subscribe(mealDTO -> {
                        view.setIsLoading(false);
                        view.setRandomMeal(mealDTO);
                    }, throwable -> {
                        view.setIsLoading(false);
                        view.setError(throwable.getLocalizedMessage());
                    });
        } else {
            view.setIsOnline(false);
        }

    }

    public void getAllMeals() {
        view.setIsLoading(true);
        Disposable dis = mealsRepository.getAllMeals()
                .compose(new OnBackgroundTransformer<>())
                .map(detailedMealsResponse -> detailedMealsResponse.getMeals())
                .map(detailedMealDTOS -> {
                    Collections.shuffle(detailedMealDTOS);
                    return detailedMealDTOS;
                })
                .subscribe(detailedMealDTOS -> {
                    view.setAllMeals(detailedMealDTOS);
                    view.setIsLoading(false);
                }, throwable -> {
                    view.setError(throwable.getLocalizedMessage());
                    view.setIsLoading(false);
                });


    }

    public void getMealById(String mealId) {
        Disposable dis =  mealsRepository.getMealByIdFromRemote(mealId)
                .compose(new OnBackgroundTransformer<>())
                .map(detailedMealsResponse -> detailedMealsResponse.getMeals().get(0))
                .subscribe(mealDTO -> {
                    view.goToMeal(mealDTO.getIdMeal());
                }, throwable -> {
                    view.setError(throwable.getLocalizedMessage());
                });
    }

    public void addMealToFavorite(DetailedMealDTO mealDTO) {

        Disposable dis = mealsRepository.addMealToLocalFavorite(
                new MealEntity(mealDTO, usersRepository.getCurrentUser().getUid(), mealDTO.getIdMeal())
        )
        .compose(new OnBackgroundTransformer<>())
        .subscribe(() -> {
                view.setSuccessfullyAddedToFavorite(mealDTO);
                Log.i(TAG, "addMealToFavorite: finished adding to favorite");
            },
            throwable -> {
                view.setError(throwable.getLocalizedMessage());
                Log.e(TAG, "addMealToFavorite: ", throwable);
            });
    }


    public void addMealToPlans(DetailedMealDTO mealDTO, Long date) {
//        plansRepository.addPlanToRemote(
//                new PlanEntity(usersRepository.getCurrentUser().getUid(), date, mealDTO.getIdMeal(), mealDTO)
//        );

        Disposable dis = plansRepository.addPlanToLocal(
                new PlanEntity(usersRepository.getCurrentUser().getUid(), date, mealDTO.getIdMeal(), mealDTO)
        ).compose(new OnBackgroundTransformer<>())
                .subscribe(() -> {
                    view.setSuccessMessage("added " + mealDTO.getStrMeal() + " to Plans Successfully");
                }, throwable -> {
                    view.setError("failed to add" + mealDTO.getStrMeal() + " to Plans");
                });
    }
}

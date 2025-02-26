package io.github.boodyahmedhamdy.mealano.details.presenter;

import android.util.Log;

import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.MealEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.PlansRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.details.contract.IMealDetailsPresenter;
import io.github.boodyahmedhamdy.mealano.details.contract.MealDetailsView;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.reactivex.rxjava3.disposables.Disposable;

public class MealDetailsPresenter implements IMealDetailsPresenter {

    private static final String TAG = "MealDetailsPresenter";
    MealDetailsView view;
    MealsRepository mealsRepository;

    UsersRepository usersRepository;

    PlansRepository plansRepository;

    NetworkMonitor networkMonitor;

    public MealDetailsPresenter(MealDetailsView view, MealsRepository mealsRepository, UsersRepository usersRepository, PlansRepository plansRepository, NetworkMonitor networkMonitor) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.usersRepository = usersRepository;
        this.plansRepository = plansRepository;
        this.networkMonitor = networkMonitor;
    }

    @Override
    public void getMealById(String mealId) {
        view.setIsLoading(true);
        if(networkMonitor.isConnected()) { // online
            view.setIsOnline(true);
            Disposable disposable =  mealsRepository.getMealByIdFromRemote(mealId)
                    .compose(new OnBackgroundTransformer<>())
                    .map(detailedMealsResponse -> detailedMealsResponse.getMeals().get(0))
                    .subscribe(mealDTO -> {
                        Log.e(TAG, "Meal ID: " + mealDTO.getIdMeal() +  " with error: " + mealDTO);
                        view.setMeal(mealDTO);
                        view.setIsLoading(false);
                    }, throwable -> {
                        view.setErrorMessage(throwable.getLocalizedMessage());
                        view.setIsLoading(false);
                    });

        } else { // offline
            // from meals
            Disposable dis = mealsRepository.getMealByIdFromLocal(mealId)
                    .compose(new OnBackgroundTransformer<>())
                    .map(mealEntity -> mealEntity.mealDTO)
                    .subscribe(mealDTO -> {
                        view.setMeal(mealDTO);
                        view.setIsLoading(false);
                        view.setIsOnline(true);
                    }, throwable -> {

                        // from plans
                        Disposable dis2 = plansRepository.getPlanFromLocalByMealId(mealId)
                                .compose(new OnBackgroundTransformer<>())
                                .map(planEntity -> planEntity.getMealDTO())
                                .subscribe(mealDTO -> {
                                    view.setMeal(mealDTO);
                                    view.setIsLoading(false);
                                    view.setIsOnline(true);
                                }, throwable1 -> {
                                    view.setErrorMessage(throwable1.getLocalizedMessage());
                                    view.setIsLoading(false);
                                    view.setIsOnline(false);
                                });
                    });

        }

    }

    public void addMealToFavorite(DetailedMealDTO mealDTO) {
        Log.i(TAG, "addMealToFavorite: started adding to favorite");

        if(usersRepository.getCurrentUser() != null) {
            Disposable dis = mealsRepository.addMealToLocalFavorite(
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
        } else {
            view.setErrorMessage("You can't add meal to favorite as a Guest!\n Login to enable this feature");
        }

    }

    public void addMealToPlans(DetailedMealDTO currentMeal, Long date) {
        PlanEntity planEntity = new PlanEntity(
                usersRepository.getCurrentUser().getUid(), date, currentMeal.getIdMeal(), currentMeal
        );

        Disposable dis = plansRepository.addPlanToLocal(planEntity)
                .compose(new OnBackgroundTransformer<>())
                .subscribe(() -> {
                    view.setSuccessMessage("added " + currentMeal.getStrMeal() + " to Plans Successfully");
                }, throwable -> {
                    view.setErrorMessage("failed to add" + currentMeal.getStrMeal() + " to Plans");
                });

//        if(networkMonitor.isConnected()) {
//            plansRepository.addPlanToRemote(planEntity)
//                    .addOnCompleteListener(task -> {
//                       if(task.isSuccessful()) {
//                           view.setSuccessMessage("successfully added " + currentMeal.getStrMeal() + " to Plans");
//                       } else {
//                           view.setErrorMessage("Failed to add " + currentMeal.getStrMeal() + " to Plans");
//                       }
//                    });
//        } else {
//            view.setErrorMessage("you need to be Online to add Plan to favorite");
//        }
    }
}

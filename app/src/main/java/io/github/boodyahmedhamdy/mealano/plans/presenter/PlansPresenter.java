package io.github.boodyahmedhamdy.mealano.plans.presenter;

import android.util.Log;

import java.util.List;
import java.util.stream.Collectors;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.PlansRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.plans.contract.PlansView;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class PlansPresenter {

    private static final String TAG = "PlansPresenter";
    PlansView view;
    PlansRepository plansRepository;
    UsersRepository usersRepository;
    NetworkMonitor networkMonitor;

    public PlansPresenter(PlansView view, PlansRepository plansRepository, UsersRepository usersRepository, NetworkMonitor networkMonitor) {
        this.view = view;
        this.plansRepository = plansRepository;
        this.usersRepository = usersRepository;
        this.networkMonitor = networkMonitor;
    }

    public void getAllPlans() {
        if(usersRepository.isLoggedIn()){
            view.setIsAuthenticated(true);

            Disposable dis = plansRepository.getAllPlansFromLocal(
                usersRepository.getCurrentUser().getUid()
            ).compose(new OnBackgroundTransformer<>())
                .subscribe(planEntities -> {
                    view.setPlans(planEntities);
                    Log.i(TAG, "getAllPlans: " + planEntities);
                }, throwable -> {
                    view.setErrorMessage(throwable.getLocalizedMessage());
                });
        } else {
            view.setIsAuthenticated(false);
        }
    }



    // to get only the upcoming plans
    public void getAllPlansByDate(Long selectedDate) {
        if(usersRepository.isLoggedIn()){
            view.setIsAuthenticated(true);
            Disposable dis = plansRepository.getPlansFromLocalByDate(
                            usersRepository.getCurrentUser().getUid(), selectedDate
                    ).compose(new OnBackgroundTransformer<>())
                    .subscribe(planEntities -> {
                        view.setPlans(planEntities);
                        Log.i(TAG, "getAllPlans: " + planEntities);
                    }, throwable -> {
                        view.setErrorMessage(throwable.getLocalizedMessage());
                    });
        } else {
            view.setIsAuthenticated(false);
        }
    }


    public void getMealById(String mealId) {
        view.goToMealDetailsScreen(mealId);
    }

    public void deletePlan(PlanEntity planEntity) {

        // deletes from local
//        Disposable dis = plansRepository.deletePlanFromLocal(planEntity)
//                .compose(new OnBackgroundTransformer<>())
//                .subscribe(() -> {
//                    view.setSuccessMessage("deleted Successfully");
//                }, throwable -> {
//                    view.setErrorMessage(throwable.getLocalizedMessage());
//                });

        // delete from remote
        if(networkMonitor.isConnected()) {
            plansRepository.deletePlanFromRemote(planEntity)
                    .addOnSuccessListener(unused -> {
                        view.setSuccessMessage("Plan deleted successfully");
                        syncV2();
                    })
                    .addOnFailureListener(e -> {
                        view.setErrorMessage("Couldn't Delete the Plan");
                    });
        } else {
            view.setErrorMessage("Can't delete plan when you are offline");
        }

    }

    public void syncV2() {
        if(usersRepository.isLoggedIn()) {
            Disposable dis = plansRepository.getAllPlansFromRemote(usersRepository.getCurrentUser().getUid())
                    .compose(new OnBackgroundTransformer<>())
                    .subscribe(remotePlanEntities -> {

                        Disposable dis2 = plansRepository.insertAllPlansToLocal(remotePlanEntities)
                                .compose(new OnBackgroundTransformer<>())
                                .subscribe(() -> {

                                    Disposable dis3 = plansRepository.deletePlansFromLocalNotIn(
                                            usersRepository.getCurrentUser().getUid(),
                                            remotePlanEntities.stream().map(planEntity -> planEntity.getMealId()).collect(Collectors.toList())
                                    ).compose(new OnBackgroundTransformer<>())
                                            .subscribe(() -> {
                                                view.setSuccessMessage("Sync Successfully");
                                            }, throwable -> { // error while deleting
                                                view.setErrorMessage("error while deleting");
                                            });

                                }, throwable -> {
                                    // error while inserting
                                    view.setErrorMessage("error while inserting");
                                });

                    }, throwable -> {
                        // error while getting from remote
                        view.setErrorMessage("error while getting from remote");
                    });

        }
    }

    public void sharePlanToCalendar(PlanEntity planEntity) {
        view.setSuccessMessage("clicked to share: " + planEntity.getMealDTO().getStrMeal());
    }
}

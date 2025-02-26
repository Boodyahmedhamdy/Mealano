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
        Disposable dis = plansRepository.deletePlanFromLocal(planEntity)
                .compose(new OnBackgroundTransformer<>())
                .subscribe(() -> {
                    view.setSuccessMessage("deleted Successfully");
                }, throwable -> {
                    view.setErrorMessage(throwable.getLocalizedMessage());
                });

/*        if(networkMonitor.isConnected()) {
            plansRepository.deletePlanFromRemote(planEntity)
                    .addOnSuccessListener(unused -> {
                        view.setSuccessMessage("Plan deleted successfully");
                        syncPlans();
                    })
                    .addOnFailureListener(e -> {
                        view.setErrorMessage("Couldn't Delete the Plan");
                    });
        } else {
            view.setErrorMessage("Can't delete plan when you are offline");
        }*/

    }


    public void syncPlans() {
        Disposable dis =  plansRepository
                .getAllPlansFromRemote(usersRepository.getCurrentUser().getUid())
                .compose(new OnBackgroundTransformer<>())
                .subscribe(planEntities -> {

                    Disposable dis2 =  plansRepository.insertAllPlansToLocal(planEntities)
                            .compose(new OnBackgroundTransformer<>())
                            .subscribe(() -> {
                                view.setSuccessMessage("Sync Successfully");
                            }, throwable -> {
                                view.setErrorMessage("Sync Failed");
                            });
                }, throwable -> {
                    view.setErrorMessage("Failed to connect to Server");
                });

    }

}

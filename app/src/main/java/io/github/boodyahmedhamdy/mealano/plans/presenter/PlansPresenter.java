package io.github.boodyahmedhamdy.mealano.plans.presenter;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.PlansRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.plans.contract.PlansView;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.reactivex.rxjava3.disposables.Disposable;

public class PlansPresenter {

    PlansView view;
    PlansRepository plansRepository;
    UsersRepository usersRepository;

    public PlansPresenter(PlansView view, PlansRepository plansRepository, UsersRepository usersRepository) {
        this.view = view;
        this.plansRepository = plansRepository;
        this.usersRepository = usersRepository;
    }

    public void getAllPlans() {

        Disposable dis = plansRepository.getAllPlansFromLocal(
            usersRepository.getCurrentUser().getUid()
        ).compose(new OnBackgroundTransformer<>())
            .subscribe(planEntities -> {
                view.setPlans(planEntities);
            }, throwable -> {
                view.setErrorMessage(throwable.getLocalizedMessage());
            });
    }


    public void getMealById(String mealId) {
        view.goToMealDetailsScreen(mealId);
    }

    public void deletePlan(PlanEntity planEntity) {

    }

    public void syncPlans() {
        Disposable dis =  plansRepository
                .getAllPlansFromRemote(usersRepository.getCurrentUser().getUid())
                .compose(new OnBackgroundTransformer<>())
                .subscribe(planEntities -> {

                    Disposable dis2 =  plansRepository.insertAllPlansToLocal(planEntities)
                            .compose(new OnBackgroundTransformer<>())
                            .subscribe(() -> {
                                view.setSyncMessage("Sync Successfully");
                            }, throwable -> {
                                view.setSyncMessage("Sync Failed");
                            });
                }, throwable -> {
                    view.setSyncMessage("Failed to connect to Server");
                });

    }
}

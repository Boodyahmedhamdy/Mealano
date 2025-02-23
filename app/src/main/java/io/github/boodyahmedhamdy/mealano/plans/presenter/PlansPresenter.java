package io.github.boodyahmedhamdy.mealano.plans.presenter;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.PlansRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.plans.contract.PlansView;

public class PlansPresenter {

    PlansView view;
    PlansRepository plansRepository;
    UsersRepository usersRepository;

    public PlansPresenter(PlansView view, PlansRepository plansRepository, UsersRepository usersRepository) {
        this.view = view;
        this.plansRepository = plansRepository;
        this.usersRepository = usersRepository;
    }

    public LiveData<List<PlanEntity>> getAllPlans() {

        return plansRepository.getAllPlans(
            usersRepository.getCurrentUser().getUid()
        );
    }



}

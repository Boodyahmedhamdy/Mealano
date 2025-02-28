package io.github.boodyahmedhamdy.mealano.home.presenter;

import java.util.Collections;

import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.meals.MealsRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.plans.PlansRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.users.UsersRepository;
import io.github.boodyahmedhamdy.mealano.home.contract.HomePresenter;
import io.github.boodyahmedhamdy.mealano.home.contract.HomeView;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.reactivex.rxjava3.disposables.Disposable;

public class HomePresenterImpl implements HomePresenter {
    private static final String TAG = "HomePresenter";

    HomeView view;

    MealsRepository mealsRepository;
    UsersRepository usersRepository;
    PlansRepository plansRepository;
    NetworkMonitor networkMonitor;


    public HomePresenterImpl(HomeView view, MealsRepository mealsRepository, UsersRepository usersRepository, PlansRepository plansRepository, NetworkMonitor networkMonitor) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.usersRepository = usersRepository;
        this.plansRepository = plansRepository;
        this.networkMonitor = networkMonitor;
    }

    @Override
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
                        view.setError("No Internet");
                    });
        } else {
            view.setIsOnline(false);
        }

    }

    @Override
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
                    view.setError("No Internet");
                    view.setIsLoading(false);
                });

    }

    @Override
    public void getMealById(String mealId) {
        view.goToMeal(mealId);
    }

    @Override
    public void addMealToPlans(DetailedMealDTO mealDTO, Long date) {
        if(usersRepository.isLoggedIn()) {

            if(networkMonitor.isConnected()) {

                plansRepository.addPlanToRemote(
                                new PlanEntity(usersRepository.getCurrentUser().getUid(), date, mealDTO.getIdMeal(), mealDTO)
                        ).addOnSuccessListener(unused -> {
                            view.setSuccessMessage("added " + mealDTO.getStrMeal() + " to Plans Successfully");
                        }).addOnFailureListener(e -> {
                            view.setError("failed to add" + mealDTO.getStrMeal() + " to Plans");
                        });
            } else {
                view.setError("Can't add Plan Offline, please turn wifi on");
            }
        } else {
            view.setError("you can't add plan in Guest mode, login to open this feature");
        }

    }

}

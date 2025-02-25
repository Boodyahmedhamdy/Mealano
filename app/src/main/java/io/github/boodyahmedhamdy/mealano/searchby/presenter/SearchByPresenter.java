package io.github.boodyahmedhamdy.mealano.searchby.presenter;

import android.util.Log;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.constants.SearchConstants;
import io.github.boodyahmedhamdy.mealano.data.network.dto.SimpleMealDTO;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.MealsRepository;
import io.github.boodyahmedhamdy.mealano.searchby.contract.SearchByView;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchByPresenter {
    private static final String TAG = "SearchByPresenter";
    SearchByView view;
    MealsRepository mealsRepository;
    NetworkMonitor networkMonitor;

    Observable<SimpleMealDTO> mealsObs;

    public SearchByPresenter(SearchByView view, MealsRepository mealsRepository, NetworkMonitor networkMonitor) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.networkMonitor = networkMonitor;
        mealsObs = Observable.fromIterable(List.of());
    }

    public void getAll(String key, String value) {
        if(networkMonitor.isConnected()) {
            view.setIsOnline(true);
            switch (key) {
                case SearchConstants.AREA: {
                    getAllMealsByArea(value);
                    break;
                } case SearchConstants.CATEGORY: {
                    getAllMealsByCategory(value);
                    break;
                } case SearchConstants.INGREDIENT: {
                    getAllMealsByIngredient(value);
                    break;
                } default: {
                    view.setErrorMessage("Unknown key sent");
                    break;
                }
            }

        } else {
            view.setIsOnline(false);
        }
    }

    private void getAllMealsByIngredient(String ingredient) {
        view.setIsLoading(true);
        Disposable dis =  mealsRepository.getAllMealsByIngredient(ingredient)
                .compose(new OnBackgroundTransformer<>())
                .map(simpleMealsResponse -> simpleMealsResponse.getMeals())
                .subscribe(simpleMealDTOS -> {
                    view.setList(simpleMealDTOS);
                    mealsObs = Observable.fromIterable(simpleMealDTOS);
                    view.setIsLoading(false);
                    Log.i(TAG, "getAllMealsByIngredient: got list of ingredients with size of " + simpleMealDTOS.size());
                }, throwable -> {
                    view.setErrorMessage(throwable.getLocalizedMessage());
                    view.setIsLoading(false);
                });
    }

    private void getAllMealsByCategory(String category) {
        view.setIsLoading(true);
        Disposable dis = mealsRepository.getAllMealsByCategory(category)
                .compose(new OnBackgroundTransformer<>())
                .map(simpleMealsResponse -> simpleMealsResponse.getMeals())
                .subscribe(simpleMealDTOS -> {
                    view.setList(simpleMealDTOS);
                    mealsObs = Observable.fromIterable(simpleMealDTOS);
                    view.setIsLoading(false);
                }, throwable -> {
                    view.setErrorMessage(throwable.getLocalizedMessage());
                    view.setIsLoading(false);
                });



    }

    private void getAllMealsByArea(String area) {
        view.setIsLoading(true);
        Disposable dis = mealsRepository.getAllMealsByArea(area)
                .compose(new OnBackgroundTransformer<>())
                .map(simpleMealsResponse -> simpleMealsResponse.getMeals())
                .subscribe(simpleMealDTOS -> {
                    view.setList(simpleMealDTOS);
                    mealsObs = Observable.fromIterable(simpleMealDTOS);
                    view.setIsLoading(false);
                }, throwable -> {
                    view.setErrorMessage(throwable.getLocalizedMessage());
                    view.setIsLoading(false);
                });
    }

    public void searchBy(String title) {
        Disposable dis = mealsObs.filter(simpleMealDTO -> {
            return simpleMealDTO.getStrMeal().toLowerCase().contains(title.toLowerCase());
        })
                .toList()
                .subscribe(simpleMealDTOS -> {
                    view.setList(simpleMealDTOS);
                }, throwable -> {
                    view.setErrorMessage(throwable.getLocalizedMessage());
                });
    }
}

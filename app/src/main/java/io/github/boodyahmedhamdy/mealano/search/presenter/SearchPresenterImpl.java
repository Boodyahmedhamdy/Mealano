package io.github.boodyahmedhamdy.mealano.search.presenter;

import android.util.Log;

import java.util.List;
import java.util.stream.Collectors;

import io.github.boodyahmedhamdy.mealano.datalayer.repos.meals.MealsRepository;
import io.github.boodyahmedhamdy.mealano.search.contract.SearchPresenter;
import io.github.boodyahmedhamdy.mealano.search.contract.SearchView;
import io.github.boodyahmedhamdy.mealano.search.view.SearchItem;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchPresenterImpl implements SearchPresenter {

    private static final String TAG = "SearchPresenter";
    SearchView view;
    MealsRepository mealsRepository;
    NetworkMonitor networkMonitor;

    Observable<SearchItem> selectedListObs;

    public SearchPresenterImpl(SearchView view, MealsRepository mealsRepository, NetworkMonitor networkMonitor) {
        this.view = view;
        this.mealsRepository = mealsRepository;
        this.networkMonitor = networkMonitor;
        selectedListObs = Observable.fromIterable(List.of());
    }

    @Override
    public void searchFor(String text) {
        Disposable dis = selectedListObs
                .filter(searchItem -> searchItem.getTitle().toLowerCase().contains(text.toLowerCase()))
                .toList()
                .subscribe(searchItems -> {
                    view.setList(searchItems);
                }, throwable -> {
                    view.setErrorMessage(throwable.getLocalizedMessage());
                });
    }

    @Override
    public void getAllCategories() {

        if(networkMonitor.isConnected()) {
            view.setIsOnline(true);

            Disposable dis =  mealsRepository.getAllCategories()
                    .compose(new OnBackgroundTransformer<>())
                    .map(categoriesResponse -> categoriesResponse.getCategories())
                    .map(detailedCategoryDTOS -> {
                        return detailedCategoryDTOS.stream().map
                                (dto -> new SearchItem(dto.getStrCategory(), dto.getStrCategoryThumb())).collect(Collectors.toList());
                    }).subscribe(searchItems -> {
                        view.setList(searchItems);
                        selectedListObs = Observable.fromIterable(searchItems);
                    }, throwable -> {
                        Log.e(TAG, "getAllCategories: ", throwable);
                        view.setErrorMessage(throwable.getLocalizedMessage());
                    });

        } else {
            view.setIsOnline(false);
        }


    }

    @Override
    public void getAllIngredients() {

        if(networkMonitor.isConnected()) {
            view.setIsOnline(true);

        Disposable dis = mealsRepository.getAllIngredients()
                .compose(new OnBackgroundTransformer<>())
                .map(ingredientsResponse -> ingredientsResponse.getIngredients().stream().map(dto -> {
                    return new SearchItem(dto.getStrIngredient(), UiUtils.getIngredientImgPath(dto.getStrIngredient()));
                }).collect(Collectors.toList()))
                .subscribe(searchItems -> {
                    view.setList(searchItems);
                    selectedListObs = Observable.fromIterable(searchItems);
                }, throwable -> {
                    Log.e(TAG, "getAllIngredients: ", throwable);
                    Log.e(TAG, "from getAllIngredients in presenter");
                    view.setErrorMessage(throwable.getLocalizedMessage());
                });

        } else {
            view.setIsOnline(false);

        }

    }

    @Override
    public void getAllAreas() {

        if(networkMonitor.isConnected()) {
            view.setIsOnline(true);

        Disposable dis =  mealsRepository.getAllAreas()
                .compose(new OnBackgroundTransformer<>())
                .map(simpleAreasResponse -> simpleAreasResponse.getAreas())
                .map(simpleAreaDTOS -> simpleAreaDTOS.stream().map(
                        dto -> new SearchItem(
                                dto.getStrArea(),
                                UiUtils.getAreaImgPath(dto.getStrArea()))
                        ).collect(Collectors.toList())
                ).subscribe(searchItems -> {
                    view.setList(searchItems);
                    selectedListObs = Observable.fromIterable(searchItems);
                }, throwable -> {
                    view.setErrorMessage(throwable.getLocalizedMessage());
                });

        } else {
            view.setIsOnline(false);

        }


    }

}

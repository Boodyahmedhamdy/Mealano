package io.github.boodyahmedhamdy.mealano.search.contract;

import java.util.List;

import io.github.boodyahmedhamdy.mealano.search.presenter.SearchItem;

public interface SearchView {
    void setIsOnline(boolean isOnline);

    void setErrorMessage(String errorMessage);

    void setList(List<SearchItem> searchItems);
}

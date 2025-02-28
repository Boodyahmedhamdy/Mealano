package io.github.boodyahmedhamdy.mealano.search.contract;

public interface SearchPresenter {
    void searchFor(String text);

    void getAllCategories();

    void getAllIngredients();

    void getAllAreas();
}

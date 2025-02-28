package io.github.boodyahmedhamdy.mealano.searchby.contract;

public interface SearchByPresenter {
    void getAll(String key, String value);

    void getAllMealsByIngredient(String ingredient);

    void getAllMealsByCategory(String category);

    void getAllMealsByArea(String area);

    void searchBy(String title);
}

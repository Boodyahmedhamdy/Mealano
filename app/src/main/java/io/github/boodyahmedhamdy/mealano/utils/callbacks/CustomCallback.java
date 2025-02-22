package io.github.boodyahmedhamdy.mealano.utils.callbacks;

public interface CustomCallback<T> {
    void onSuccess(T data);
    void onFailure(String errorMessage);
}

package io.github.boodyahmedhamdy.mealano.utils.network;

public interface CustomNetworkCallback<T>{

    void onSuccess(T data);
    void onFailure(String errorMessage);
}

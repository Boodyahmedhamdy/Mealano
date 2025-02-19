package io.github.boodyahmedhamdy.mealano.login.contract;

public interface OnLoginCallBack {
    void onSuccess();
    void onFailure(String errorMessage);
}

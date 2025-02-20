package io.github.boodyahmedhamdy.mealano.profile.contract;

public interface OnSignOutCallback {
    void onSuccess();
    void onFailure(String errorMessage);
}

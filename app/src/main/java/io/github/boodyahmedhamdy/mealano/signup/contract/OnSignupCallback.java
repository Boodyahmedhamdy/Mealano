package io.github.boodyahmedhamdy.mealano.signup.contract;

public interface OnSignupCallback {
    void onSuccess();
    void onFailure(String errorMessage);
}

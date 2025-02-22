package io.github.boodyahmedhamdy.mealano.login.contract;

public interface LoginView {
    void setErrorMessage(String errorMessage);
    void setEmailValidationErrorMessage(String errorMessage);
    void setPasswordValidationError(String errorMessage);
    void setIsLoading(Boolean isLoading);

    void goToHomeScreen();

}

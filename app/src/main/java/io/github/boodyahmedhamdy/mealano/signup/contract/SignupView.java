package io.github.boodyahmedhamdy.mealano.signup.contract;

public interface SignupView {

    void setIsLoading(Boolean isLoading);
    void setEmailValidationError(String errorMessage);
    void setPasswordValidationError(String errorMessage);
    void setConfirmPasswordValidationError(String errorMessage);
    void setErrorMessage(String errorMessage);

    void goToHomeScreen();
}

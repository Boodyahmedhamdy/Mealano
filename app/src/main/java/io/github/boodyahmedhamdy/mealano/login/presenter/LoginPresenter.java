package io.github.boodyahmedhamdy.mealano.login.presenter;


import static io.github.boodyahmedhamdy.mealano.constants.AuthConstants.MIN_PASSWORD_LENGTH;

import android.util.Log;
import android.util.Patterns;

import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.login.contract.LoginView;
import io.github.boodyahmedhamdy.mealano.login.contract.OnLoginCallBack;

public class LoginPresenter {
    private static final String TAG = "LoginPresenter";
    LoginView view;
    UsersRepository usersRepository;

    public LoginPresenter(LoginView view, UsersRepository usersRepository) {
        this.view = view;
        this.usersRepository = usersRepository;
    }

    public void loginWithEmailAndPassword(String email, String password, OnLoginCallBack callBack) {
        Log.i(TAG, "signUpWithEmailAndPassword: started");
        if(isValidInput(email, password)) {
            view.setIsLoading(true);
            view.setErrorMessage("");
            usersRepository.loginWithEmailAndPassword(email, password, callBack);
        } else {
            view.setErrorMessage("input isn't valid");
            view.setIsLoading(false);
        }
    }

    private boolean isValidInput(String email, String password) {
        Log.i(TAG, "isValidInput: started validating");
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.setEmailValidationErrorMessage("please enter a valid Email, " + email + " isn't valid");
            Log.i(TAG, "isValidInput: finished validating email false");
            return false;
        }
        if(password.trim().length() < MIN_PASSWORD_LENGTH) {
            view.setPasswordValidationError("password must be at least " + MIN_PASSWORD_LENGTH + " letters");
            Log.i(TAG, "isValidInput: finished validating password false");
            return false;
        }
        Log.i(TAG, "isValidInput: finished validating true");
        return true;
    }

}

package io.github.boodyahmedhamdy.mealano.signup.presenter;

import android.util.Log;
import android.util.Patterns;

import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.signup.contract.OnSignupCallback;
import io.github.boodyahmedhamdy.mealano.signup.contract.SignupView;

public class SignupPresenter {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String TAG = "SignupPresenter";

    SignupView view;

    UsersRepository usersRepository;

    public SignupPresenter(SignupView view, UsersRepository usersRepository) {
        this.view = view;
        this.usersRepository = usersRepository;
    }

    public void signupWithEmailAndPassword(
            String email, String password, String confirmPassword,
            OnSignupCallback callback
    ) {
        Log.i(TAG, "signupWithEmailAndPassword: started");
        if(isValidInput(email, password, confirmPassword)) {
            view.setIsLoading(true);
            usersRepository.signupWithEmailAndPassword(email, password, callback);
        }

    }

    private boolean isValidInput(String email, String password, String confirmPassword) {
        Log.i(TAG, "isValidInput: started validating");
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.setEmailValidationError("please enter a valid Email, " + email + " isn't valid");
            Log.i(TAG, "isValidInput: finished validating email false");
            return false;
        }
        if(password.trim().length() < MIN_PASSWORD_LENGTH) {
            view.setPasswordValidationError("password must be at least " + MIN_PASSWORD_LENGTH + " letters");
            Log.i(TAG, "isValidInput: finished validating password false");
            return false;
        }
        if(!password.equals(confirmPassword)) {
            view.setConfirmPasswordValidationError("confirm password isn't the same as password");
            Log.i(TAG, "isValidInput: finished validating confirmPassword false");
            return false;
        }
        Log.i(TAG, "isValidInput: finished validating true");
        return true;
    }




}

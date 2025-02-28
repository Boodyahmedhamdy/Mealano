package io.github.boodyahmedhamdy.mealano.login.presenter;


import static io.github.boodyahmedhamdy.mealano.constants.AuthConstants.MIN_PASSWORD_LENGTH;

import android.util.Log;
import android.util.Patterns;

import com.google.firebase.auth.AuthCredential;

import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.login.contract.LoginPresenter;
import io.github.boodyahmedhamdy.mealano.login.contract.LoginView;

public class LoginPresenterImpl implements LoginPresenter {
    private static final String TAG = "LoginPresenter";
    LoginView view;
    UsersRepository usersRepository;

    public LoginPresenterImpl(LoginView view, UsersRepository usersRepository) {
        this.view = view;
        this.usersRepository = usersRepository;
    }

    @Override
    public void loginWithEmailAndPassword(String email, String password) {
        Log.i(TAG, "signUpWithEmailAndPassword: started");
        if(isValidInput(email, password)) {
            view.setIsLoading(true);
            usersRepository.loginWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            view.goToHomeScreen();
                        } else {
                            view.setPasswordValidationError("");
                            view.setEmailValidationErrorMessage("");
                            view.setErrorMessage(task.getException().getLocalizedMessage());
                            view.setIsLoading(false);
                        }
                    });

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
        view.setEmailValidationErrorMessage("");

        if(password.trim().length() < MIN_PASSWORD_LENGTH) {
            view.setPasswordValidationError("password must be at least " + MIN_PASSWORD_LENGTH + " letters");
            Log.i(TAG, "isValidInput: finished validating password false");
            return false;
        }
        view.setPasswordValidationError("");

        Log.i(TAG, "isValidInput: finished validating true");
        return true;
    }

    @Override
    public void signInWithCredential(AuthCredential credential) {
        usersRepository.signInWithCredential(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                view.goToHomeScreen();
            } else {
                view.setErrorMessage(task.getException().getLocalizedMessage());
            }
        });
    }

}

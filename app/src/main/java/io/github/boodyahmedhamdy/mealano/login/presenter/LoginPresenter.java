package io.github.boodyahmedhamdy.mealano.login.presenter;


import static io.github.boodyahmedhamdy.mealano.constants.AuthConstants.MIN_PASSWORD_LENGTH;

import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.login.contract.LoginView;
import io.github.boodyahmedhamdy.mealano.utils.network.EmptyNetworkCallback;

public class LoginPresenter {
    private static final String TAG = "LoginPresenter";
    LoginView view;
    UsersRepository usersRepository;

    public LoginPresenter(LoginView view, UsersRepository usersRepository) {
        this.view = view;
        this.usersRepository = usersRepository;
    }

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

}

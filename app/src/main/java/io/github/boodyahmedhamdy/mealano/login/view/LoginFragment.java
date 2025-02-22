package io.github.boodyahmedhamdy.mealano.login.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.SharedPreferencesManager;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentLoginBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.UsersLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.UsersRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.login.contract.LoginView;
import io.github.boodyahmedhamdy.mealano.login.presenter.LoginPresenter;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;

public class LoginFragment extends Fragment implements LoginView {
    private static final String TAG = "LoginFragment";
    FragmentLoginBinding binding;
    NavController navController;
    LoginPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.hideToolbar(requireActivity());
        UiUtils.hideBottomBar(requireActivity());
        navController = Navigation.findNavController(binding.getRoot());

        presenter = new LoginPresenter(
                this,
                UsersRepository.getInstance(
                        new UsersLocalDataSource(
                                SharedPreferencesManager.getInstance(getContext()),
                                FirebaseAuth.getInstance()
                        ),
                        new UsersRemoteDataSource(FirebaseAuth.getInstance())
                )
        );


        binding.tvGoToSignup.setOnClickListener(v -> {
            navController.navigate(
                    LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            );
        });
        binding.btnContinueAsGuest.setOnClickListener(v -> {
            navController.navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            );
        });
        binding.btnLogin.setOnClickListener(v -> {

            String email = binding.tietEmail.getText().toString();
            String password = binding.tietPassword.getText().toString();

            Log.i(TAG, "btn login clicked with " + email + " and " + password);
            presenter.loginWithEmailAndPassword(email, password);
            Log.i(TAG, "Line after presenter.signUpWithEmailAndPassword()");

        });


    }

    @Override
    public void setErrorMessage(String errorMessage) {
        Log.e(TAG, "setErrorMessage: " + errorMessage);
        binding.tvLoginError.setText(errorMessage);
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.error_happened)
                .setMessage(R.string.some_error_happend_while_logging_you_in_please_check_your_internet_connection_and_try_again)
                .setIcon(R.drawable.baseline_error_outline_24)
                .setPositiveButton(
                        "Ok", (dialog1, which) -> {
                            Log.i(TAG, "ok button clicked in dialog");
                            changeInProgress(false);
                        }
                )
                .setMessage(errorMessage)
                .create();
        dialog.show();
    }

    @Override
    public void setEmailValidationErrorMessage(String errorMessage) {
        binding.tilEmail.setError(errorMessage);
    }

    @Override
    public void setPasswordValidationError(String errorMessage) {
        binding.tilPassword.setError(errorMessage);
    }

    @Override
    public void setIsLoading(Boolean isLoading) {
        changeInProgress(isLoading);
    }

    @Override
    public void goToHomeScreen() {
        Snackbar.make(binding.getRoot(), R.string.login_successfully, Snackbar.LENGTH_LONG).show();
        navController.navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        );
    }

    private void changeInProgress(boolean inProgress) {
        if (inProgress) {
            binding.getRoot().setAlpha(0.5f);
            binding.pbLogin.setVisibility(VISIBLE);
            binding.pbLogin.setAlpha(1);
            binding.btnLogin.setVisibility(INVISIBLE);
        } else {
            binding.pbLogin.setVisibility(INVISIBLE);
            binding.btnLogin.setVisibility(VISIBLE);
            binding.getRoot().setAlpha(1);
        }
    }


}
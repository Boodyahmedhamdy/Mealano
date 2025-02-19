package io.github.boodyahmedhamdy.mealano.signup.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.SharedPreferencesManager;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentSignupBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.UsersLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.UsersRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.signup.contract.OnSignupCallback;
import io.github.boodyahmedhamdy.mealano.signup.contract.SignupView;
import io.github.boodyahmedhamdy.mealano.signup.presenter.SignupPresenter;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class SignupFragment extends Fragment implements SignupView, OnSignupCallback {
    private static final String TAG = "SignupFragment";
    FragmentSignupBinding binding;
    SharedPreferencesManager sharedPreferencesManager;
    SignupPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.hideToolbar(requireActivity());
        UiUtils.hideBottomBar(requireActivity());
        presenter = new SignupPresenter(
                this,
                new UsersRepository(
                        new UsersLocalDataSource(),
                        new UsersRemoteDataSource(FirebaseAuth.getInstance())
                )
        );

        sharedPreferencesManager = SharedPreferencesManager.getInstance(requireActivity());

        binding.tvGoToLogin.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(
                    SignupFragmentDirections.actionSignupFragmentToLoginFragment()
            );
        });
        binding.btnContinueAsGuest.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(
                    SignupFragmentDirections.actionSignupFragmentToHomeFragment()
            );
        });
        binding.btnSignup.setOnClickListener(v -> {
            String email = binding.tietEmailSignupScreen.getText().toString();
            String password = binding.tietPasswordSignupScreen.getText().toString();
            String confirmPassword = binding.tietConfirmPasswordSignupScreen.getText().toString();
            Log.i(TAG, "onViewCreated: clicked on signup Button from SignupFragment");

            presenter.signupWithEmailAndPassword(email, password, confirmPassword, this);
            Log.i(TAG, "onViewCreated: line after presenter.signupWithEmailAndPassword");

        });
    }

    private void changeInProgress(boolean inProgress) {
        if (inProgress) {
            binding.pbSignup.setVisibility(VISIBLE);
            binding.btnSignup.setVisibility(GONE);
        } else {
            binding.pbSignup.setVisibility(GONE);
            binding.btnSignup.setVisibility(VISIBLE);
        }
    }


    @Override
    public void setIsLoading(Boolean isLoading) {
        changeInProgress(isLoading);
    }

    @Override
    public void setEmailValidationError(String errorMessage) {
        binding.tietEmailSignupScreen.setError(errorMessage);
    }

    @Override
    public void setPasswordValidationError(String errorMessage) {
        binding.tietPasswordSignupScreen.setError(errorMessage);
    }

    @Override
    public void setConfirmPasswordValidationError(String errorMessage) {
        binding.tietConfirmPasswordSignupScreen.setError(errorMessage);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        Log.e(TAG, "setErrorMessage: " + errorMessage);
        binding.tvError.setText(errorMessage);
    }

    @Override
    public void onSuccess() {
        Navigation.findNavController(binding.getRoot()).
                navigate(SignupFragmentDirections.actionSignupFragmentToHomeFragment());
    }

    @Override
    public void onFailure(String errorMessage) {
        AlertDialog dialog = new AlertDialog.Builder(binding.getRoot().getContext())
                .setTitle("Error While Signing up")
                .setMessage(errorMessage).create();
        dialog.show();
    }
}
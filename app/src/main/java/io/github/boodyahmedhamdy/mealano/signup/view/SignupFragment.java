package io.github.boodyahmedhamdy.mealano.signup.view;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.SharedPreferencesManager;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentSignupBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.users.UsersLocalDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.users.UsersRemoteDataSourceImpl;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.users.UsersRepositoryImpl;
import io.github.boodyahmedhamdy.mealano.signup.contract.SignupView;
import io.github.boodyahmedhamdy.mealano.signup.presenter.SignupPresenter;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class SignupFragment extends Fragment implements SignupView {
    private static final String TAG = "SignupFragment";
    private static final int SIGNUP_REQUEST_CODE = 10;
    FragmentSignupBinding binding;
    SignupPresenter presenter;
    NavController navController;

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
                UsersRepositoryImpl.getInstance(
                        UsersLocalDataSourceImpl.getInstance(
                                SharedPreferencesManager.getInstance(getContext()),
                                FirebaseAuth.getInstance()
                        ),
                        UsersRemoteDataSourceImpl.getInstance(FirebaseAuth.getInstance())
                )
        );
        navController = Navigation.findNavController(view);


        binding.tvGoToLogin.setOnClickListener(v -> {
            navController.navigate(
                    SignupFragmentDirections.actionSignupFragmentToLoginFragment()
            );
        });
        binding.btnContinueAsGuest.setOnClickListener(v -> {
            navController.navigate(
                    SignupFragmentDirections.actionSignupFragmentToHomeFragment()
            );
        });
        binding.btnSignup.setOnClickListener(v -> {
            String email = binding.tietEmailSignupScreen.getText().toString();
            String password = binding.tietPasswordSignupScreen.getText().toString();
            String confirmPassword = binding.tietConfirmPasswordSignupScreen.getText().toString();
            Log.i(TAG, "onViewCreated: clicked on signup Button from SignupFragment");

            presenter.signupWithEmailAndPassword(email, password, confirmPassword);
            Log.i(TAG, "onViewCreated: line after presenter.signupWithEmailAndPassword");

        });

        binding.ivLoginWithGoogle.setOnClickListener(v -> {
            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.client_id))
                    .requestEmail()
                    .build();

            GoogleSignInClient client = GoogleSignIn.getClient(requireActivity(), signInOptions);

            Intent intent = client.getSignInIntent();
            startActivityForResult(intent, SIGNUP_REQUEST_CODE);
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGNUP_REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                presenter.signupWithCredential(credential);
            } catch (Exception e) {
                e.printStackTrace();
                setErrorMessage(e.getLocalizedMessage());
            }
        }
    }

    private void changeInProgress(boolean inProgress) {
        if (inProgress) {
            binding.getRoot().setAlpha(0.5f);
            binding.pbSignup.setAlpha(1);
            binding.pbSignup.setVisibility(VISIBLE);
            binding.btnSignup.setVisibility(INVISIBLE);

        } else {
            binding.getRoot().setAlpha(1);
            binding.pbSignup.setVisibility(INVISIBLE);
            binding.btnSignup.setVisibility(VISIBLE);
        }
    }


    @Override
    public void setIsLoading(Boolean isLoading) {
        changeInProgress(isLoading);
    }

    @Override
    public void setEmailValidationError(String errorMessage) {
        binding.tilEmailSignupScreen.setError(errorMessage);
    }

    @Override
    public void setPasswordValidationError(String errorMessage) {
        binding.tilPasswordSignupScreen.setError(errorMessage);
    }

    @Override
    public void setConfirmPasswordValidationError(String errorMessage) {
        binding.tilConfirmPasswordSignupScreen.setError(errorMessage);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        Log.e(TAG, "setErrorMessage: " + errorMessage);
        binding.tvError.setText(errorMessage);
        AlertDialog dialog = new AlertDialog.Builder(binding.getRoot().getContext())
                .setTitle(R.string.error_happened)
                .setMessage(errorMessage).create();
        dialog.show();
    }

    @Override
    public void goToHomeScreen() {
        Snackbar.make(binding.getRoot(), R.string.signup_successfully, Snackbar.LENGTH_LONG).show();
        Navigation.findNavController(binding.getRoot()).
                navigate(SignupFragmentDirections.actionSignupFragmentToHomeFragment());
    }

}
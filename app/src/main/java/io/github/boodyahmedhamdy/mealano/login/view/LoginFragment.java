package io.github.boodyahmedhamdy.mealano.login.view;

import static android.view.View.GONE;
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

import com.google.firebase.auth.FirebaseAuth;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentLoginBinding;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.UsersLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.UsersRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.login.contract.LoginView;
import io.github.boodyahmedhamdy.mealano.login.contract.OnLoginCallBack;
import io.github.boodyahmedhamdy.mealano.login.presenter.LoginPresenter;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;

public class LoginFragment extends Fragment implements LoginView, OnLoginCallBack {
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
                new UsersRepository(
                        new UsersLocalDataSource(),
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


            presenter.signUpWithEmailAndPassword(email, password, this);

        });


    }

    @Override
    public void setErrorMessage(String errorMessage) {
        binding.tvLoginError.setText(errorMessage);
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
    private void changeInProgress(boolean inProgress) {
        if (inProgress) {
            binding.pbLogin.setVisibility(VISIBLE);
            binding.btnLogin.setVisibility(GONE);
        } else {
            binding.pbLogin.setVisibility(GONE);
            binding.btnLogin.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onSuccess() {
        navController.navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        );
    }

    @Override
    public void onFailure(String errorMessage) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("error Sign in")
                .setIcon(R.drawable.baseline_error_outline_24)
                .setPositiveButton(
                        "Positive", (dialog1, which) -> Log.i(TAG, "ok button clicked in dialog")
                )
                .setNegativeButton(
                        "Negative", (dialog1, which) -> Log.i(TAG, "negative button clicked")
                )
                .setNeutralButton(
                        "Neutral", (dialog1, which) -> Log.i(TAG, "Neutral button clicked")
                )
                .setMessage(errorMessage)
                .create();
        dialog.show();

    }
}
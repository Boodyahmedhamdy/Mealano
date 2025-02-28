package io.github.boodyahmedhamdy.mealano.splash.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.SharedPreferencesManager;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.UsersLocalDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.datasources.remote.UsersRemoteDataSource;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.UsersRepository;
import io.github.boodyahmedhamdy.mealano.splash.contract.SplashView;
import io.github.boodyahmedhamdy.mealano.splash.contract.SplashPresenter;
import io.github.boodyahmedhamdy.mealano.splash.presenter.SplashPresenterImpl;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;


public class SplashFragment extends Fragment implements SplashView {

    private static final String TAG = "SplashFragment";
    private static final long SPLASH_SCREEN_TIME_IN_MILLIS = 2000;
    SplashPresenter presenter;
    NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.hideToolbar(requireActivity());
        UiUtils.hideBottomBar(requireActivity());
        navController = Navigation.findNavController(view);

        presenter = new SplashPresenterImpl(
                this,
                UsersRepository.getInstance(
                        UsersLocalDataSource.getInstance(
                                SharedPreferencesManager.getInstance(getContext()),
                                FirebaseAuth.getInstance()
                        ),
                        UsersRemoteDataSource.getInstance(FirebaseAuth.getInstance())
                )
        );

        new Handler().postDelayed(() -> {

            presenter.start();

        }, SPLASH_SCREEN_TIME_IN_MILLIS);

    }

    @Override
    public void goToOnBoarding() {
        Log.i(TAG, "setIsFirstTime: navigating to OnBoarding");
        navController.navigate(
                SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment()
        );
    }

    @Override
    public void goToHome() {
        Log.i(TAG, "setIsLoggedIn: navigating to Home");
        navController.navigate(
                SplashFragmentDirections.actionSplashFragmentToHomeFragment()
        );
    }

    @Override
    public void goToLogin() {
        Log.i(TAG, "setIsLoggedIn: navigating to Login");
        navController.navigate(
                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
        );
    }
}
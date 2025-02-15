package io.github.boodyahmedhamdy.mealano.splash;

import static android.view.View.GONE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.SharedPreferencesManager;


public class SplashFragment extends Fragment {


    private static final long SPLASH_SCREEN_TIME_IN_MILLIS = 2000;
    SharedPreferencesManager sharedPreferencesManager;

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
        hideToolbar();
        hideBottomBar();
        sharedPreferencesManager = SharedPreferencesManager.getInstance(getActivity());

        new Handler().postDelayed(() -> {
            NavController navController = Navigation.findNavController(view);
            if(isFirstTime()) {
                // navigate to onboarding screens
            } else {
                if(userLoggedIn()) {
                    navController.navigate(
                            SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                    );
                } else { // not logged in
                    navController.navigate(
                            SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                    );
                }
            }

        }, SPLASH_SCREEN_TIME_IN_MILLIS);

    }

    private boolean isFirstTime() {
        return sharedPreferencesManager.isFirstTime();
    }

    private void hideToolbar() {
        getActivity().findViewById(R.id.toolbar).setVisibility(GONE);
    }
    private void hideBottomBar() {
        getActivity().findViewById(R.id.bottomNavigationView).setVisibility(GONE);
    }

    private boolean userLoggedIn() {
        return sharedPreferencesManager.isUserLoggedIn();
    }
}
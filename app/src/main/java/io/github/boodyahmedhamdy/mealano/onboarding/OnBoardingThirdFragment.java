package io.github.boodyahmedhamdy.mealano.onboarding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.SharedPreferencesManager;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;

public class OnBoardingThirdFragment extends Fragment {
    Button btnNext;
    ViewPager2 viewPager2;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_third, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.hideToolbar(requireActivity());
        UiUtils.hideBottomBar(requireActivity());
        viewPager2 = requireActivity().findViewById(R.id.viewPager);
        btnNext = view.findViewById(R.id.btnFinishToLogin);

        btnNext.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Navigating To Login", Toast.LENGTH_SHORT).show();
            sharedPreferencesManager = SharedPreferencesManager.getInstance(getContext());
            sharedPreferencesManager.setIsFirstTime(false); // indicates that user saw onboarding before
            Navigation.findNavController(view).navigate(
                    OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment()
            );

        });
    }
}
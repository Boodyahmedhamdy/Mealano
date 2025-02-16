package io.github.boodyahmedhamdy.mealano.onboarding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;

public class OnBoardingFirstFragment extends Fragment {
    
    Button btnNext;
    ViewPager2 viewPager2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.hideToolbar(requireActivity());
        UiUtils.hideBottomBar(requireActivity());
        viewPager2 = requireActivity().findViewById(R.id.viewPager);
        btnNext = view.findViewById(R.id.btnNextToSecondOnboarding);
        btnNext.setOnClickListener(v -> {
            viewPager2.setCurrentItem(1, true);
        });
    }
}
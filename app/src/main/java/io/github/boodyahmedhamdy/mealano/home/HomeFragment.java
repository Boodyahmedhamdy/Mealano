package io.github.boodyahmedhamdy.mealano.home;

import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.boodyahmedhamdy.mealano.R;


public class HomeFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar();
        showBottomBar();
    }

    private void showToolbar() {
        getActivity().findViewById(R.id.toolbar).setVisibility(VISIBLE);
    }

    private void showBottomBar() {
        getActivity().findViewById(R.id.bottomNavigationView).setVisibility(VISIBLE);
    }


}
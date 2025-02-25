package io.github.boodyahmedhamdy.mealano.searchby.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.databinding.FragmentSearchByBinding;
import io.github.boodyahmedhamdy.mealano.utils.ui.UiUtils;

public class SearchByFragment extends Fragment {
    private static final String TAG = "SearchByFragment";
    FragmentSearchByBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchByBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UiUtils.hideBottomBar(requireActivity());
        String key = SearchByFragmentArgs.fromBundle(getArguments()).getSearchByKey();
        String value = SearchByFragmentArgs.fromBundle(getArguments()).getSearchByValue();

        Snackbar.make(binding.getRoot(), "key: " + key + ", value: " + value, Snackbar.LENGTH_LONG)
                .setBackgroundTint(getResources().getColor(R.color.md_theme_error))
                .setTextColor(getResources().getColor(R.color.md_theme_onError))
                .setAction("Dismiss", v -> {
                    Log.i(TAG, "onViewCreated: clicked on action");
                })
                .show();

    }
}
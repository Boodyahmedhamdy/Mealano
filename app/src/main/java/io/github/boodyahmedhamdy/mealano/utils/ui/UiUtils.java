package io.github.boodyahmedhamdy.mealano.utils.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.fragment.app.FragmentActivity;

import io.github.boodyahmedhamdy.mealano.R;

public class UiUtils {


    public static void hideToolbar(FragmentActivity fragmentActivity) {
        fragmentActivity.findViewById(R.id.toolbar).setVisibility(GONE);
    }
    public static void hideBottomBar(FragmentActivity fragmentActivity) {
        fragmentActivity.findViewById(R.id.bottomNavigationView).setVisibility(GONE);
    }

    public static void showToolbar(FragmentActivity fragmentActivity) {
        fragmentActivity.findViewById(R.id.toolbar).setVisibility(VISIBLE);
    }

    public static void showBottomBar(FragmentActivity fragmentActivity) {
        fragmentActivity.findViewById(R.id.bottomNavigationView).setVisibility(VISIBLE);
    }

}

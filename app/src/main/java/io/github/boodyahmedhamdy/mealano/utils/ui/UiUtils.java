package io.github.boodyahmedhamdy.mealano.utils.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.fragment.app.FragmentActivity;

import java.util.HashMap;
import java.util.Map;

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


    public static final Map<String, Integer> flags = Map.ofEntries(
            Map.entry("American", R.drawable.america_meduim),
            Map.entry("British", R.drawable.england_meduim),
            Map.entry("Canadian", R.drawable.canada_meduim),
            Map.entry("Chinese", R.drawable.china_meduim),
            Map.entry("Croatian", R.drawable.coratia_meduim),
            Map.entry("Dutch", R.drawable.germany_meduim)
    );


}

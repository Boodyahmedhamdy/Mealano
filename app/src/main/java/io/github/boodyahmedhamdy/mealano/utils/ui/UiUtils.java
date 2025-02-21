package io.github.boodyahmedhamdy.mealano.utils.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.fragment.app.FragmentActivity;

import java.util.List;
import java.util.Map;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.dto.DetailedMealDTO;

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

    public static String transformYoutubeUrl(String youtubeUrl) {
        if (youtubeUrl == null || youtubeUrl.isEmpty()) {
            return "";
        }

        // Check if the URL contains "watch?v="
        if (youtubeUrl.contains("watch?v=")) {
            // Replace "watch?v=" with "embed/"
            return youtubeUrl.replace("watch?v=", "embed/");
        }
        return youtubeUrl; // Return original URL if it doesn't match the expected format
    }

    public static String getEmbedYoutubeHtml(String youtubeUrl) {
        return "<iframe width=\"100%\" height=\"100%\"" +
                " src=\"" + youtubeUrl + "\"" +
                " title=\"YouTube video player\"" +
                " frameborder=\"0\"" +
                " allow=\"accelerometer; autoplay; clipboard-write;" +
                " encrypted-media; gyroscope; picture-in-picture;" +
                " web-share\" referrerpolicy=\"strict-origin-when-cross-origin\"" +
                " allowfullscreen></iframe>";
    }

    public static List<SimpleIngredientAndMeasurement> getIngredientsAndMeasurements(DetailedMealDTO mealDTO) {
        List<String> measurements = List.of(
                mealDTO.getStrMeasure1(),
                mealDTO.getStrMeasure2(),
                mealDTO.getStrMeasure3(),
                mealDTO.getStrMeasure4(),
                mealDTO.getStrMeasure5(),
                mealDTO.getStrMeasure6(),
                mealDTO.getStrMeasure7(),
                mealDTO.getStrMeasure8(),
                mealDTO.getStrMeasure9(),
                mealDTO.getStrMeasure10(),
                mealDTO.getStrMeasure11(),
                mealDTO.getStrMeasure12(),
                mealDTO.getStrMeasure13(),
                mealDTO.getStrMeasure14(),
                mealDTO.getStrMeasure15(),
                mealDTO.getStrMeasure16(),
                mealDTO.getStrMeasure17(),
                mealDTO.getStrMeasure18(),
                mealDTO.getStrMeasure19(),
                mealDTO.getStrMeasure20()
        );

        List<String> ingredients = List.of(
                mealDTO.getStrIngredient1(),
                mealDTO.getStrIngredient2(),
                mealDTO.getStrIngredient3(),
                mealDTO.getStrIngredient4(),
                mealDTO.getStrIngredient5(),
                mealDTO.getStrIngredient6(),
                mealDTO.getStrIngredient7(),
                mealDTO.getStrIngredient8(),
                mealDTO.getStrIngredient9(),
                mealDTO.getStrIngredient10(),
                mealDTO.getStrIngredient11(),
                mealDTO.getStrIngredient12(),
                mealDTO.getStrIngredient13(),
                mealDTO.getStrIngredient14(),
                mealDTO.getStrIngredient15(),
                mealDTO.getStrIngredient16(),
                mealDTO.getStrIngredient17(),
                mealDTO.getStrIngredient18(),
                mealDTO.getStrIngredient19(),
                mealDTO.getStrIngredient20()
        );
        List<SimpleIngredientAndMeasurement> components = List.of();
        for(int i = 0 ; i < ingredients.size() ; i++) {
            if(ingredients.get(i) == null || measurements.get(i) == null) {
                break;
            } else {
                String bigImageUrl = getIngredientBigImageUrl(ingredients.get(i));
                String smallImageUrl = getIngredientSmallImageUrl(ingredients.get(i));
                components.add(
                        new SimpleIngredientAndMeasurement(ingredients.get(i), measurements.get(i), smallImageUrl, bigImageUrl)
                );
            }
        }
        return components;
    }

    private static String getIngredientSmallImageUrl(String ingredient) {
        return "https://www.themealdb.com/images/ingredients/" + ingredient + "-small.png";
    }

    private static String getIngredientBigImageUrl(String ingredient) {
        return "https://www.themealdb.com/images/ingredients/" + ingredient + ".png";

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

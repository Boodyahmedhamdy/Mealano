package io.github.boodyahmedhamdy.mealano.utils.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.boodyahmedhamdy.mealano.R;
import io.github.boodyahmedhamdy.mealano.data.network.dtos.DetailedMealDTO;

public class UiUtils {

    private static final String TAG = "UiUtils";
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
        try {
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
            List<SimpleIngredientAndMeasurement> components = new ArrayList<>();
            for(int i = 0 ; i < ingredients.size() ; i++) {
                if(
                        ingredients.get(i) == null
                                || ingredients.get(i).isEmpty()
                                || measurements.get(i) == null
                                || measurements.get(i).isEmpty()
                ) {
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


        } catch (Exception e) {
            Log.e(TAG, "getIngredientsAndMeasurements: ", e);
            return new ArrayList<>();
        }



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
            Map.entry("Dutch", R.drawable.germany_meduim),
            Map.entry("Egyptian", R.drawable.egypt_meduim),
            Map.entry("Filipino", R.drawable.baseline_broken_image_24), // not found
            Map.entry("French", R.drawable.france_meduim),
            Map.entry("Greek", R.drawable.greek_meduim),
            Map.entry("Indian", R.drawable.india_meduim), // done
            Map.entry("Irish", R.drawable.ireland_meduim),
            Map.entry("Italian", R.drawable.italy_meduim),
            Map.entry("Jamaican", R.drawable.jamica_meduim),
            Map.entry("Japanese", R.drawable.japan_meduim),
            Map.entry("Kenyan", R.drawable.kenya_meduim),
            Map.entry("Malaysian", R.drawable.malasya_meduim), // done
            Map.entry("Mexican", R.drawable.mixeco_meduim),
            Map.entry("Moroccan", R.drawable.moroco_meduim),
            Map.entry("Norwegian", R.drawable.norway_meduim), // done
            Map.entry("Polish", R.drawable.poland_meduim), // done
            Map.entry("Portuguese", R.drawable.portogal_meduim), // done
            Map.entry("Russian", R.drawable.russia_meduim), // done
            Map.entry("Spanish", R.drawable.spain_meduim), // done
            Map.entry("Thai", R.drawable.thai_meduim), // done
            Map.entry("Tunisian", R.drawable.tunis_meduim), // done
            Map.entry("Turkish", R.drawable.turkey_meduim), // done
            Map.entry("Ukrainian", R.drawable.ukrain_meduim), // done
            Map.entry("Uruguayan", R.drawable.ukrain_meduim), // done
            Map.entry("Vietnamese", R.drawable.vitnam_meduim) // done
    );


    public static String getIngredientImgPath(String strIngredient) {
        return "https://www.themealdb.com/images/ingredients/" + strIngredient + "-small.png";
    }

    private static Map<String, String> countryCodeMap = Map.ofEntries(
        Map.entry("American", "US"),
        Map.entry("British", "GB"),
        Map.entry("Canadian", "CA"),
        Map.entry("Chinese", "CN"),
        Map.entry("Croatian", "HR"),
        Map.entry("Dutch", "NL"),
        Map.entry("Egyptian", "EG"),
        Map.entry("Filipino", "PH"),
        Map.entry("French", "FR"),
        Map.entry("Greek", "GR"),
        Map.entry("Indian", "IN"),
        Map.entry("Irish", "IE"),
        Map.entry("Italian", "IT"),
        Map.entry("Jamaican", "JM"),
        Map.entry("Japanese", "JP"),
        Map.entry("Kenyan", "KE"),
        Map.entry("Malaysian", "MY"),
        Map.entry("Mexican", "MX"),
        Map.entry("Moroccan", "MA"),
        Map.entry("Norwegian", "NO"),
        Map.entry("Polish", "PL"),
        Map.entry("Portuguese", "PT"),
        Map.entry("Russian", "RU"),
        Map.entry("Spanish", "ES"),
        Map.entry("Thai", "TH"),
        Map.entry("Tunisian", "TN"),
        Map.entry("Turkish", "TR"),
        Map.entry("Ukrainian", "UA"),
        Map.entry("Uruguayan", "UY"),
        Map.entry("Vietnamese", "VN")
    );

    public static String getAreaImgPath(String strArea) {
        String countryCode = countryCodeMap.getOrDefault(strArea, "unknown");
        return "https://www.themealdb.com/images/icons/flags/big/64/" + countryCode.toLowerCase() + ".png";
    }

    public static void showSuccessSnackBar(View view, String successMessage) {
        try {
            Snackbar.make(view, successMessage, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(view.getResources().getColor(R.color.md_theme_primary))
                    .setTextColor(view.getResources().getColor(R.color.md_theme_onPrimary))
                    .show();
        } catch (Exception e) {
            Log.e(TAG, "showSuccessSnackBar: ", e);
        }


    }

    public static void showErrorSnackBar(View view, String errorMessage) {
        try {
            Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(view.getResources().getColor(R.color.md_theme_error))
                    .setTextColor(view.getResources().getColor(R.color.md_theme_onError))
                    .show();

        } catch (Exception e) {
            Log.e(TAG, "showErrorSnackBar: ", e);
        }
    }
}

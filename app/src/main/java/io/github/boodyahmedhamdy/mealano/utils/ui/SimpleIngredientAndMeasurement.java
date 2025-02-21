package io.github.boodyahmedhamdy.mealano.utils.ui;

public class SimpleIngredientAndMeasurement {
    private String ingredient;
    private String measurement;
    private String smallImageUrl;
    private String bigImageUrl;

    public SimpleIngredientAndMeasurement(String ingredient, String measurement, String smallImageUrl, String bigImageUrl) {
        this.ingredient = ingredient;
        this.measurement = measurement;
        this.smallImageUrl = smallImageUrl;
        this.bigImageUrl = bigImageUrl;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }
}

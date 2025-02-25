package io.github.boodyahmedhamdy.mealano.search.presenter;

public class SearchItem {

    private String title;
    private String imgPath;

    public SearchItem(String title, String imgPath) {
        this.title = title;
        this.imgPath = imgPath;
    }


    @Override
    public String toString() {
        return "SearchItem{" +
                "title='" + title + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }

    public SearchItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}

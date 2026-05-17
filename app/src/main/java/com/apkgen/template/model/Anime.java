package com.apkgen.template.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Anime {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("titleO")
    private String titleOriginal;

    @SerializedName("affiche")
    private String posterUrl;

    @SerializedName("banner")
    private String bannerUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("note")
    private String rating;

    @SerializedName("themes")
    private List<String> themes;

    @SerializedName("status")
    private String status;

    @SerializedName("format")
    private String format;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("nsfw")
    private boolean nsfw;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getTitleOriginal() { return titleOriginal; }
    public String getPosterUrl() { return posterUrl; }
    public String getBannerUrl() { return bannerUrl; }
    public String getDescription() { return description; }
    public String getRating() { return rating; }
    public List<String> getThemes() { return themes; }
    public String getStatus() { return status; }
    public String getFormat() { return format; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public boolean isNsfw() { return nsfw; }

    public String getShortDescription() {
        if (description == null) return "Pas de description";
        if (description.length() > 100) {
            return description.substring(0, 100) + "...";
        }
        return description;
    }

    public String getThemesAsString() {
        if (themes == null || themes.isEmpty()) return "Non classé";
        return String.join(", ", themes);
    }

    @Override
    public String toString() {
        return "Anime{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
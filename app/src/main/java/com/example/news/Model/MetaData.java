package com.example.news.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MetaData {

    @SerializedName("version")
    @Expose
    private String version;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("home_page_url")
    @Expose
    private String home_page_url;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("items")
    @Expose
    private List<Items> items;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHome_page_url() {
        return home_page_url;
    }

    public void setHome_page_url(String home_page_url) {
        this.home_page_url = home_page_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}

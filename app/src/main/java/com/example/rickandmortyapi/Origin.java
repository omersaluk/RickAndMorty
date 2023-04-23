package com.example.rickandmortyapi;

import com.google.gson.annotations.SerializedName;

public class Origin {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("url")
    private String url;

    // Getter and setter methods

}

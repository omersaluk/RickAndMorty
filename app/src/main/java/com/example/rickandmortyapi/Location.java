package com.example.rickandmortyapi;

public class Location {

    public Location(String name, String url) {
        this.name = name;
    }

    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public String getName() {
        return name;
    }
}

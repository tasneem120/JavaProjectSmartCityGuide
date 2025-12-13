package com.example.javaprojectsmartcityguide.model;

public class Places {
    private int id;
    private String name;
    private String description;
    private String imageUrl;   // matches database name image_url

    public Places(int id, String name, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

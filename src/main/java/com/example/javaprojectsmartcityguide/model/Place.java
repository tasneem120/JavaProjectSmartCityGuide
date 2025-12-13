package com.example.javaprojectsmartcityguide.model;

public class Place {
    private int id;
    private String name;
    private String category;
    private String location;
    private double price;
    private int rating;
    private String description;
    private String imageUrl;

    // Constructor (لازم يكون فيه الـ 8 بيانات دول)
    public Place(int id, String name, String category, String location, double price, int rating, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.location = location;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public double getPrice() { return price; }
    public int getRating() { return rating; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
}
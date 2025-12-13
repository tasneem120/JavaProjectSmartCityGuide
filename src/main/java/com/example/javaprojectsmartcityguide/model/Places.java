package com.example.javaprojectsmartcityguide.model;

public class Places {
    private String name;
    private String description;
    private String imgSrc;


    public Places(String name, String description, String imgSrc) {
        this.name = name;
        this.description = description;
        this.imgSrc = imgSrc;
    }


    public Places() {}


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}

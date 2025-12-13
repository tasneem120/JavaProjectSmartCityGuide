package com.example.javaprojectsmartcityguide.controller;

import com.example.javaprojectsmartcityguide.model.Places;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Items {

    @FXML
    private ImageView img;

    @FXML
    private Label namelable;

    private Places placeList;

    public void setData(Places placeList) {
        this.placeList = placeList;
        namelable.setText(placeList.getName());

        Image image = new Image(
                getClass().getResourceAsStream(placeList.getImgSrc())
        );
        img.setImage(image);
    }
}

package com.example.javaprojectsmartcityguide;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class detailsController {

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblDescription;

    @FXML
    private ImageView eventImage;
    @FXML


    public void setData(String title, String date, String time, String imagePath, String desc) {
        lblTitle.setText(title);
        lblDate.setText(date);
        lblTime.setText(time);
        lblDescription.setText(desc);

        String fullPath = "/com/example/javaprojectsmartcityguide/Images/" + imagePath;
        eventImage.setImage(new Image(getClass().getResourceAsStream(fullPath)));



        }
}


package com.example.javaprojectsmartcityguide;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReviewItemController {

    @FXML private Label lblUsername;
    @FXML private Label lblPlaceName;
    @FXML private Label lblStars;
    @FXML private Label lblComment;

    public void setData(String user, String place, int rating, String comment) {
        rating = Math.max(0, Math.min(5, rating));
        lblUsername.setText(user);
        lblPlaceName.setText("Place: " + place);
        lblComment.setText(comment);

        String stars = "★".repeat(rating) + "☆".repeat(5 - rating);
        lblStars.setText(stars);
    }
}

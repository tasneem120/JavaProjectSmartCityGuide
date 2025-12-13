package com.example.javaprojectsmartcityguide;

import com.example.javaprojectsmartcityguide.model.BDConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReviewsController {
    @FXML private VBox reviewsContainer;

    @FXML private TextField userNameField;
    @FXML private TextField placeNameField;
    @FXML private TextField ratingField;
    @FXML private TextArea commentField;


    @FXML
    public void initialize() {
        loadReviews();
    }

    private void loadReviews() {
        String sql = "SELECT user_name, place_name, rating, comment FROM reviews  " ;

        try (Connection conn = BDConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            reviewsContainer.getChildren().clear();

            while (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaprojectsmartcityguide/reviewscard.fxml"));
                Parent reviewNode = loader.load();

                ReviewItemController controller = loader.getController();
                controller.setData(
                        rs.getString("user_name"),
                        rs.getString("place_name"),
                        rs.getInt("rating"),
                        rs.getString("comment")
                );

                reviewsContainer.getChildren().add(reviewNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSubmit() {
        String userName = userNameField.getText().trim();
        String placeName = placeNameField.getText().trim();
        String comment = commentField.getText().trim();
        int rating;

        // Validate rating
        try {
            rating = Integer.parseInt(ratingField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Invalid Rating", "Rating must be a number between 1 and 5.");
            return;
        }

        // Validate fields
        if (userName.isEmpty() || placeName.isEmpty() || comment.isEmpty()) {
            showAlert("Missing Data", "Please fill in all fields.");
            return;
        }
        // Add review to database
        addReview(userName,placeName,rating,comment);
        // clear text feild
        userNameField.clear();
        placeNameField.clear();
        ratingField.clear();
        commentField.clear();
    }

    @FXML
    private void addReview(String userName, String placeName, int rating, String comment) {
        String sql = "INSERT INTO reviews (user_name, place_name, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = BDConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, placeName);
            ps.setInt(3, rating);
            ps.setString(4, comment);

            ps.executeUpdate();

            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backToWelcome(javafx.event.ActionEvent event) {
        try {
            // Load the welcome page
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/example/javaprojectsmartcityguide/Welcome .fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void refresh() {
        loadReviews();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package com.example.javaprojectsmartcityguide;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReviewsController {

    @FXML private VBox reviewsContainer;

    @FXML
    public void initialize() {
        loadReviews();
    }

    private void loadReviews() {
        String sql = "SELECT u.username, p.name, r.rating, r.comment " +
                "FROM reviews r " +
                "JOIN users u ON r.UserID = u.UserID " +
                "JOIN places p ON r.PlaceID = p.PlaceID";

        try (Connection conn = BDConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            reviewsContainer.getChildren().clear();

            while (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("review_item.fxml"));
                Parent reviewNode = loader.load();

                ReviewItemController controller = loader.getController();
                controller.setData(
                        rs.getString("username"),
                        rs.getString("name"),
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
    private void addReview(String userName, String placeName, int rating, String comment) {
        String sql = "INSERT INTO reviews (UserID, PlaceID, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = BDConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int userId = DBMethods.getUserID(userName);
            int placeId = DBMethods.getPlaceID(placeName);

            ps.setInt(1, userId);
            ps.setInt(2, placeId);
            ps.setInt(3, rating);
            ps.setString(4, comment);

            ps.executeUpdate();

            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        loadReviews();
    }
}

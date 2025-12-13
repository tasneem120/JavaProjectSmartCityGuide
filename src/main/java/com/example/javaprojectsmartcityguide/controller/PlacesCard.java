package com.example.javaprojectsmartcityguide.controller;

import com.example.javaprojectsmartcityguide.BDConnection;
import com.example.javaprojectsmartcityguide.model.Places;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlacesCard {

    @FXML
    private Label namelable;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ImageView imageView;

    private final List<Places> placesList = new ArrayList<>();
    private int currentIndex = 0;

    // 🔹 تحميل من جدول حسب الكاتيجوري
    public void loadPlacesFromTable(String tableName) throws SQLException, ClassNotFoundException {

        Connection conn = BDConnection.getConnection();
        if (conn == null) {
            System.out.println("DB connection failed");
            return;
        }

        placesList.clear();

        String sql = "SELECT * FROM " + tableName;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                placesList.add(new Places(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("image_url")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!placesList.isEmpty()) {
            currentIndex = 0;
            showPlace();
        } else {
            namelable.setText("");
            descriptionArea.setText("No data found");
            imageView.setImage(null);
        }
    }

    // 🔹 عرض الصف الحالي
    private void showPlace() {

        Places place = placesList.get(currentIndex);

        namelable.setText(place.getName());
        descriptionArea.setWrapText(true);
        descriptionArea.setText(place.getDescription());

        // تعديل المسار للتأكد أنه صحيح
        String imagePath = "/com/example/javaprojectsmartcityguide/" + place.getImageUrl();
//        System.out.println(imagePath);
        InputStream stream = getClass().getResourceAsStream(imagePath);

        if (stream == null) {
            System.out.println("Image not found: " + imagePath);
            imageView.setImage(null);
            return;
        }

        imageView.setImage(new Image(stream));
    }

    @FXML
    private void nextSlide() {
        if (currentIndex < placesList.size() - 1) {
            currentIndex++;
            showPlace();
        }
    }

    @FXML
    private void previousSlide() {
        if (currentIndex > 0) {
            currentIndex--;
            showPlace();
        }
    }
}

package com.example.javaprojectsmartcityguide.controller;


import com.example.javaprojectsmartcityguide.controller.PlacesCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewVisitor implements Initializable {

    @FXML
    private ComboBox<String> pageSelector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pageSelector.getItems().addAll(
                "Restaurants",
                "Hotels",
                "Entertainment"
        );

        pageSelector.setOnAction(e -> openPlacesCard());
    }

    private void openPlacesCard() {

        String selectedCategory = pageSelector.getValue();
        if (selectedCategory == null) return;

        // 🔹 نحول اسم الكاتيجوري لاسم جدول
        String tableName = switch (selectedCategory) {
            case "Restaurants" -> "restaurants";
            case "Hotels" -> "hotels";
            case "Entertainment" -> "entertainment";
            default -> null;
        };

        if (tableName == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojectsmartcityguide/PlacesCard.fxml")
            );

            Parent root = loader.load();

            PlacesCard controller = loader.getController();
            controller.loadPlacesFromTable(tableName);

            Stage stage = new Stage();
            stage.setTitle("Places - " + selectedCategory);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

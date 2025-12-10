package com.example.javaprojectsmartcityguide.controller;

import com.example.javaprojectsmartcityguide.model.Places;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewVisitor implements Initializable {

    @FXML
    private VBox ImageCard;

    @FXML
    private ScrollPane ScrollPane;

    @FXML
    private GridPane grid;

    @FXML
    private ComboBox<String> pageSelector;

    private List<Places> placeList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        pageSelector.getItems().addAll(
                "Restaurants & Coffee",
                "Hotels",
                "Public Services"
        );

        pageSelector.setOnAction(e -> openPlacesCard());

        loadDataToUI();
    }

    private List<Places> getData() {
        List<Places> placesList = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Places place = new Places(
                    "Luxury Place " + (i + 1),
                    "Perfect for your vacation",
                    "/Images/hotel3.jpg"
            );
            placesList.add(place);
        }
        return placesList;
    }

    private void loadDataToUI() {
        placeList.addAll(getData());
        int column = 0;
        int row = 0;

        try {
            for (int i = 0; i < placeList.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader(
                        getClass().getResource(
                                "resources/com/example/javaprojectsmartcityguide/items.fxml"
                        )
                );

                AnchorPane anchorPane = fxmlLoader.load();

                Items items = fxmlLoader.getController();
                items.setData(placeList.get(i));

                if (column == 4) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openPlacesCard() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "resources/com/example/javaprojectsmartcityguide/PlacesCard.fxml"
                    )
            );

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Places");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




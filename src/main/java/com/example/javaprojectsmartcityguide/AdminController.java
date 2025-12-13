package com.example.javaprojectsmartcityguide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class AdminController extends Application {

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> categoryBox;

    @FXML
    private TextField locationField;

    @FXML
    private TextField priceField;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<?> placesTable;

    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colCategory;
    @FXML
    private TableColumn<?, ?> colLocation;
    @FXML
    private TableColumn<?, ?> colPrice;
    @FXML
    private TableColumn<?, ?> colRating;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 750, 530);
        stage.setTitle("");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("Images/logo.png")));
        stage.setScene(scene);
        stage.show();
    }
}

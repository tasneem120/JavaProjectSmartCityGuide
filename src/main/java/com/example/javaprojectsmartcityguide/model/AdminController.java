package com.example.javaprojectsmartcityguide.model;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminController extends Application implements Initializable {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> categoryBox;
    @FXML private TextField locationField;
    @FXML private TextField ratingField; // تأكدي إنك ضفتي ده في SceneBuilder بنفس الاسم
    @FXML private TextField searchField;

    @FXML private TableView<Place> placesTable;
    @FXML private TableColumn<Place, String> colName;
    @FXML private TableColumn<Place, String> colCategory;
    @FXML private TableColumn<Place, String> colLocation;
    @FXML private TableColumn<Place, Integer> colRating;

    private ObservableList<Place> placeList = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // تأكدي إن ملف FXML موجود في مكانه (Files/Resources)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/javaprojectsmartcityguide/admin_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Smart City Guide - Admin");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. إعداد الأعمدة
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        // 2. إعداد القائمة المنسدلة
        categoryBox.getItems().addAll("Entertainment", "Hotels", "Restaurants&Cafe");

        // 3. تحميل البيانات
        loadDataFromDatabase();

        // 4. ملء الخانات عند الضغط على الجدول
        placesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection.getName());
                categoryBox.setValue(newSelection.getCategory());
                locationField.setText(newSelection.getLocation());
                ratingField.setText(String.valueOf(newSelection.getRating()));
            }
        });

        // 5. تفعيل البحث
        setupSearch();
    }

    // --- قسم التعامل مع الداتا بيز ---

    private void loadDataFromDatabase() {
        placeList.clear();
        String query = "SELECT * FROM places";
        try {
            Connection conn = BDConnection.getConnection(); // هنا بننادي الكلاس اللي معانا في نفس الفولدر
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                placeList.add(new Place(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("location"),
                        rs.getDouble("price"),
                        rs.getInt("rating"),
                        rs.getString("description"),
                        rs.getString("image_url")
                ));
            }
            placesTable.setItems(placeList);
            setupSearch(); // تحديث البحث بعد تحميل البيانات

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addPlace(ActionEvent event) {
        String name = nameField.getText();
        String category = categoryBox.getValue();
        String location = locationField.getText();
        String ratingStr = ratingField.getText();

        if (name.isEmpty() || category == null || location.isEmpty() || ratingStr.isEmpty()) {
            showAlert("Warning", "Please fill all fields!");
            return;
        }

        try {
            int rating = Integer.parseInt(ratingStr);

            // بنضيف البيانات الأساسية، والباقي بنحطله قيم افتراضية عشان الداتا بيز تقبل
            String query = "INSERT INTO places (name, category, location, rating, price, description, image_url) VALUES (?, ?, ?, ?, 0.0, 'No Description', '')";

            Connection conn = BDConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setString(3, location);
            pstmt.setInt(4, rating);

            pstmt.executeUpdate();

            clearFields();
            loadDataFromDatabase();

        } catch (NumberFormatException e) {
            showAlert("Error", "Rating must be a number (e.g. 5)!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updatePlace(ActionEvent event) {
        Place selectedPlace = placesTable.getSelectionModel().getSelectedItem();
        if (selectedPlace == null) {
            showAlert("Error", "Please select a place to update!");
            return;
        }

        try {
            int rating = Integer.parseInt(ratingField.getText());
            String query = "UPDATE places SET name=?, category=?, location=?, rating=? WHERE id=?";

            Connection conn = BDConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nameField.getText());
            pstmt.setString(2, categoryBox.getValue());
            pstmt.setString(3, locationField.getText());
            pstmt.setInt(4, rating);
            pstmt.setInt(5, selectedPlace.getId());

            pstmt.executeUpdate();
            clearFields();
            loadDataFromDatabase();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deletePlace(ActionEvent event) {
        Place selectedPlace = placesTable.getSelectionModel().getSelectedItem();
        if (selectedPlace == null) return;

        try {
            String query = "DELETE FROM places WHERE id=?";
            Connection conn = BDConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, selectedPlace.getId());

            pstmt.executeUpdate();
            clearFields();
            loadDataFromDatabase();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- أدوات مساعدة ---

    private void setupSearch() {
        FilteredList<Place> filteredData = new FilteredList<>(placeList, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(place -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lower = newValue.toLowerCase();
                if (place.getName().toLowerCase().contains(lower)) return true;
                else if (place.getCategory().toLowerCase().contains(lower)) return true;
                else return place.getLocation().toLowerCase().contains(lower);
            });
        });
        SortedList<Place> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(placesTable.comparatorProperty());
        placesTable.setItems(sortedData);
    }

    private void clearFields() {
        nameField.clear();
        categoryBox.getSelectionModel().clearSelection();
        locationField.clear();
        ratingField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void handleBack(ActionEvent event) {
        try {
            // المسار هنا لازم يكون دقيق
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javaprojectsmartcityguide/hello-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
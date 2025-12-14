package com.example.javaprojectsmartcityguide;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> categoryBox;
    @FXML private TextField locationField;
    @FXML private TextField priceField; // التأكيد على وجود الـpriceField في ملف FXML
    @FXML private TextField searchField;

    @FXML private TableView<Place> placesTable;
    @FXML private TableColumn<Place, String> colName;
    @FXML private TableColumn<Place, String> colCategory;
    @FXML private TableColumn<Place, String> colLocation;


    private ObservableList<Place> placeList = FXCollections.observableArrayList();

//    public static void main(String[] args) {
//        launch(args);
//    }

//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/javaprojectsmartcityguide/admin_view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Smart City Guide - Admin");
//        stage.setScene(scene);
//        stage.show();
//    }



    @Override

    public void initialize(URL location, ResourceBundle resources) {
        // ربط الأعمدة بالبيانات
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));


        // إضافة الكاتجوري إلى القائمة المنسدلة
        categoryBox.getItems().addAll("Restaurants", "Hotels", "Entertainment");
        categoryBox.setValue("Restaurants"); // تعيين القيمة الافتراضية

        // تحديث الجدول عند تغيير الكاتجوري
        categoryBox.valueProperty().addListener((obs, oldCategory, newCategory) -> {
            loadDataBasedOnCategory(); // تحديث البيانات بناءً على الكاتجوري الجديدة
        });

        placesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection.getName());
                categoryBox.setValue(newSelection.getCategory());
                locationField.setText(newSelection.getDescription());
                priceField.setText(String.valueOf(newSelection.getPrice()));
            }
        });
    }
@FXML
private void loadDataBasedOnCategory() {
    // تفريغ البيانات السابقة
    placeList.clear();

    // جلب الكاتجوري المختارة من ComboBox
    String selectedCategory = categoryBox.getValue();

    // التحقق من تحديد الكاتجوري
    if (selectedCategory == null || selectedCategory.isEmpty()) {
        showAlert("Error", "Please select a category!");

        return;
    }

    // بناء الاستعلام بناءً على الكاتجوري المختارة
    String query = switch (selectedCategory) {
        case "Restaurants" -> "SELECT * FROM restaurants";
        case "Hotels" -> "SELECT * FROM hotels";
        case "Entertainment" -> "SELECT * FROM entertainment";
        default -> throw new IllegalArgumentException("Invalid Category!");
    };

    try (Connection conn = BDConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        // جلب البيانات من الجدول المناسب وإضافتها إلى القائمة
        while (rs.next()) {
            placeList.add(new Place(
                    rs.getInt("id"),             // ID للمكان
                    rs.getString("name"),         // الاسم
                    selectedCategory,            // الكاتجوري المختارة
                    rs.getString("description"), // الوصف
                    0.0,                         // السعر (افتراضي)
                    0,                           // التقييم (افتراضي)
                    rs.getString("description"), // الموقع (أو الوصف)
                    rs.getString("image_url")    // الرابط للصورة
            ));
        }

        // تحديد البيانات للجدول
        placesTable.setItems(placeList);

        System.out.println("Data Loaded for Category: " + selectedCategory);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
    void addPlace(ActionEvent event) {
        String name = nameField.getText();
        String category = categoryBox.getValue();
        String description = locationField.getText();
        String imageUrl = priceField.getText();

        try {
            String query = switch (category) {
                case "Restaurants" -> "INSERT INTO restaurants (name, description, image_url) VALUES (?, ?, ?)";
                case "Hotels" -> "INSERT INTO hotels (name, description, image_url) VALUES (?, ?, ?)";
                case "Entertainment" -> "INSERT INTO entertainment (name, description, image_url) VALUES (?, ?, ?)";
                default -> throw new IllegalArgumentException("Invalid Category!");
            };

            Connection conn = BDConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setString(3, imageUrl);

            pstmt.executeUpdate();
            clearFields();
            loadDataBasedOnCategory();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updatePlace(ActionEvent event) {
        // التأكد من تحديد الصف المراد تعديله
        Place selectedPlace = placesTable.getSelectionModel().getSelectedItem();
        if (selectedPlace == null) {
            showAlert("Error", "Please select a place to edit!");

            return;
        }

        // جلب القيم من الحقول
        String name = nameField.getText();
        String category = categoryBox.getValue();
        String description = locationField.getText();
        String imageUrl = priceField.getText();

        // التأكد من أن كل الحقول ممتلئة
        if (name.isEmpty() || category == null || description.isEmpty() || imageUrl.isEmpty()) {
            showAlert("Error", "Please fill in all fields before saving the changes!");

            return;
        }

        try {
            // بناء الاستعلام بناءً على الفئة
            String query = switch (category) {
                case "Restaurants" -> "UPDATE restaurants SET name=?, description=?, image_url=? WHERE id=?";
                case "Hotels" -> "UPDATE hotels SET name=?, description=?, image_url=? WHERE id=?";
                case "Entertainment" -> "UPDATE entertainment SET name=?, description=?, image_url=? WHERE id=?";
                default -> throw new IllegalArgumentException("Invalid Category!");
            };

            // تنفيذ التعديل على قاعدة البيانات
            Connection conn = BDConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setString(3, imageUrl);
            pstmt.setInt(4, selectedPlace.getId()); // التأكد من وجود قيمة الـID

            int rowsAffected = pstmt.executeUpdate(); // عدد الصفوف المعدلة
            if (rowsAffected > 0) {
                System.out.println("Updated Rows: " + rowsAffected);
                showAlert("Success", "Data has been updated successfully!");

            } else {
                showAlert("Warning", "No rows were updated. Please check the values!");

            }

            // تفريغ الحقول وتحميل البيانات الحديثة
            clearFields();
            loadDataBasedOnCategory();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while updating the data!");

        }
    }


    @FXML
    void deletePlace(ActionEvent event) {
        // التحقق من تحديد صف من الجدول
        Place selectedPlace = placesTable.getSelectionModel().getSelectedItem();
        if (selectedPlace == null) {
            showAlert("Error", "Please select a place to delete first!");

            return;
        }

        String category = selectedPlace.getCategory(); // جلب الفئة الخاصة بالمكان المراد مسحه

        try {
            // بناء الاستعلام بناءً على الكاتجوري
            String query = switch (category) {
                case "Restaurants" -> "DELETE FROM restaurants WHERE id=?";
                case "Hotels" -> "DELETE FROM hotels WHERE id=?";
                case "Entertainment" -> "DELETE FROM entertainment WHERE id=?";
                default -> "DELETE FROM places WHERE id=?";
            };

            // تنفيذ العملية في قاعدة البيانات
            try (Connection conn = BDConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, selectedPlace.getId()); // تمرير الـ ID للمكان المراد مسحه

                int rowsAffected = pstmt.executeUpdate(); // عدد الصفوف التي تم مسحها
                if (rowsAffected > 0) {
                    System.out.println("Row Deleted: " + rowsAffected);
                    showAlert("Success", "The place has been deleted successfully!");

                } else {
                    showAlert("Warning", "No rows were deleted. Please check the values!");

                }
            }

            // تحديث الجدول
            clearFields();
            loadDataBasedOnCategory();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while deleting the place!");

        }
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // تصفية البيانات بناءً على النص المدخل في خانة البحث
            FilteredList<Place> searchFilteredList = new FilteredList<>(placeList, place -> {
                if (newValue == null || newValue.isEmpty()) return true; // عرض جميع البيانات إذا كانت خانة البحث فارغة
                String lowerCaseFilter = newValue.toLowerCase(); // تحويل النص المدخل إلى حالة صغيرة لتجنب التمييز بين الأحرف الكبيرة والصغيرة

                return place.getName().toLowerCase().contains(lowerCaseFilter)
                        || place.getCategory().toLowerCase().contains(lowerCaseFilter)
                        || place.getLocation().toLowerCase().contains(lowerCaseFilter)
                        || place.getImageUrl().toLowerCase().contains(lowerCaseFilter);
            });


            placesTable.setItems(new SortedList<>(searchFilteredList, placesTable.getComparator()));
        });
    }

    private void clearFields() {

        nameField.clear();
        locationField.clear();
        priceField.clear();


        if (categoryBox.getItems().size() > 0) {
            categoryBox.setValue(categoryBox.getItems().get(0));
        } else {
            categoryBox.getSelectionModel().clearSelection();
        }
    }
    private void showAlert(String title, String content) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION); // نوع الرسالة (معلومات)
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("Images/logo.png").toExternalForm()));
        stage.setTitle( "Alert");
  // عنوان الرسالة
        alert.setHeaderText(null); // إزالة النص الرئيسي (Message Header)
        alert.setContentText(content); // نص الرسالة الرئيسي

        // تطبيق ملف CSS لتخصيص التصميم
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("CSS/errorMsgStyle.css").toExternalForm()); // ربط ملف CSS
        dialogPane.getStyleClass().add("alert"); // إضافة الـ CSS Class لتخصيص الرسالة

        // تخصيص الأيقونة في الرسالة
        ImageView icon = new ImageView(new Image(getClass().getResource("images/info_16821021.png").toExternalForm())); // مسار الصورة
        icon.setFitHeight(40); // حجم الأيقونة
        icon.setFitWidth(40);
        alert.setGraphic(icon); // تعيين الأيقونة

        // تخصيص النص الخاص بالزر "OK"
        alert.getButtonTypes().clear(); // إزالة الأزرار الافتراضية
        alert.getButtonTypes().add(new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE)); // زر مخصص

        alert.showAndWait();
    }
    @FXML
    void backWelcome(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("welcome .fxml"))));
    }
}
package com.example.javaprojectsmartcityguide;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {


    @FXML
    void openAdminLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login .fxml"))));
    }
    @FXML
    void visitor(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("View.fxml"))));
    }
    //link with review padge
    @FXML private Button reviewsBtn;

    @FXML private void openReviews(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("reviews.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//end here
    @FXML
    void backWelcome(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("welcome .fxml"))));
    }
    @FXML
    private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML
    private Label errorLabel; // دي هتظهر رساله الغلط


    @FXML
    void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        errorLabel.setVisible(false); // نخفيه كل مرة الصفحة تتعامل مع الـ login

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Error,Please fill all fields!");
            errorLabel.setVisible(true);
         return;
        }else {
            errorLabel.setText("");
        }
        try {
            Connection conn = BDConnection.getConnection();

            String sql = "SELECT role FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");

                if ("Admin".equals(role)) {
                    openScene("admin_view.fxml");
                } else {
                    errorLabel.setText("You do not have Admin access!");
                    errorLabel.setVisible(true);
                }
            } else {
                errorLabel.setText("Invalid username or password!");
                errorLabel.setVisible(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Database error!");
            errorLabel.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("UI / Scene loading error!");
            errorLabel.setVisible(true);
        }

    }


    private void openScene(String fxml) throws Exception {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxml))));
    }

    private void alert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(msg);
        a.show();
    }

}

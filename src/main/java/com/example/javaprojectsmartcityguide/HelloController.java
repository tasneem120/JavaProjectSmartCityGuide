/*package com.example.javaprojectsmartcityguide;
import com.example.javaprojectsmartcityguide.model.BDConnection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HelloController {
    @FXML
    void openAdminLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login.fxml"))));
    }

    @FXML
    private TextField usernameField;
    @FXML private PasswordField passwordField;
    void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            alert("Error", "Please fill all fields!");
            return;
        }

        try {
            Connection conn =BDConnection.getConnection();
            String sql = "SELECT role FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String role = rs.getString("role");
                if(role.equals("Admin")) openScene("admin.fxml");//هنا صفحه الادمن الي هتفتح لما تتحط
            } else {
                alert("Error", "Invalid username or password!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    void visitor(ActionEvent event) {
        try { openScene("user.fxml"); } catch(Exception e) { e.printStackTrace(); }//هنا همحط صفحه الزوار
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
}*/

package com.example.javaprojectsmartcityguide;

import javafx.event.ActionEvent; // ✅ لازم يكون javafx مش java.awt
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {

    @FXML
    void openAdminLogin(ActionEvent event) {
        // لما يدوس Admin نوديه لصفحة تسجيل الدخول
        navigateTo(event, "login.fxml");
    }

    @FXML
    void visitor(ActionEvent event) {
        // لما يدوس Visitor نوديه لصفحة عرض الأماكن (مثلاً View.fxml)
        // تأكدي إن اسم الملف عندك View.fxml أو admin_view.fxml
        navigateTo(event, "View.fxml");
    }

    // دالة مساعدة عشان نمنع تكرار الكود
    private void navigateTo(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not load FXML file: " + fxmlFile);
        }
    }
}
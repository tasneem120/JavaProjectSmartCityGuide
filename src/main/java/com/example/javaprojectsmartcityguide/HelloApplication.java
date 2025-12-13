package com.example.javaprojectsmartcityguide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("welcome .fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("City Explorer");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("Images/logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
launch(args);
//        try {
//            Connection conn = BDConnection.getConnection();
//            if(conn != null && !conn.isClosed()) {
//                System.out.println("✅ Database connection successful!");
//            } else {
//                System.out.println("❌ Database connection failed!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
    }
}
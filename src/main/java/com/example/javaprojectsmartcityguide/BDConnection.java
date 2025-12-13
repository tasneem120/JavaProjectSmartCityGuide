package com.example.javaprojectsmartcityguide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConnection {
public static Connection getConnection() throws ClassNotFoundException, SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/smartcityguide";
            String username = "root";
            String password = "";
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


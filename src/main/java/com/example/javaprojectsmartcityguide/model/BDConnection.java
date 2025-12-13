package com.example.javaprojectsmartcityguide.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConnection {

    // تأكدي إن اسم الداتا بيز هنا هو smartcitydatabase
    private static final String URL = "jdbc:mysql://localhost:3306/smartcitydatabase";
    private static final String USER = "root";

    // ⚠️ هام جداً: اكتبي الباسورد بتاع MySQL Workbench هنا بين علامات التنصيص
    private static final String PASSWORD = "AyaK!2025#Dev";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

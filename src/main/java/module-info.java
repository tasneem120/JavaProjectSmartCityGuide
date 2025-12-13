module com.example.javaprojectsmartcityguide {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;            // عشان الداتا بيز
    requires mysql.connector.j;   // عشان الموصل

    // تصريح للمجلد الرئيسي
    opens com.example.javaprojectsmartcityguide to javafx.fxml;
    opens com.example.javaprojectsmartcityguide.controller to javafx.fxml;
    exports com.example.javaprojectsmartcityguide;

    // 👇👇 أهم سطرين لازم يتضافوا عشان الكود بتاعك يشتغل 👇👇
    // (بيدوا تصريح لفولدر model اللي جواه AdminController و BDConnection)
    exports com.example.javaprojectsmartcityguide.model;
    opens com.example.javaprojectsmartcityguide.model to javafx.fxml;

    exports com.example.javaprojectsmartcityguide.controller;
  //  opens com.example.javaprojectsmartcityguide.controller to javafx.fxml;
}
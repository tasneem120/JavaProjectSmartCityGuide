package com.example.javaprojectsmartcityguide;

import java.sql.*;

public class DBMethods {

    // get userName from userID
    public static int getUserID(String username) {
        String sql = "SELECT UserID FROM users WHERE username = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("UserID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // only if dont find user
    }

    // get placeName from PlaceID
    public static int getPlaceID(String placeName) {
        String sql = "SELECT PlaceID FROM places WHERE name = ?";
        try (Connection conn = BDConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, placeName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("PlaceID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}


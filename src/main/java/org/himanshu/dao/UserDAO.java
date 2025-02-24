package org.himanshu.dao;

import org.himanshu.db.MyConnection;
import org.himanshu.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static boolean isUserExist(String email) throws SQLException {
        // Since the email is actually stored in the 'name' column due to the swap
        String query = "SELECT name FROM Users WHERE name = ?";

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    public static int saveUser(User user) throws SQLException {
        // Keep consistent with how data is currently stored in DB
        String query = "INSERT INTO Users (name, email) VALUES (?, ?)";

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getName());  // Store email in name column
            preparedStatement.setString(2, user.getEmail());   // Store name in email column

            return preparedStatement.executeUpdate();
        }
    }
}
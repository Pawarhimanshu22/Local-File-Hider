package org.himanshu.db;

import java.sql.*;

public class MyConnection {

    static Connection connection = null;

    public static String Load_Driver  = "com.mysql.cj.jdbc.Driver";

    public static String URL = "jdbc:mysql://localhost:3306/File_Hider";

    public static String PASSWORD = "@#IFeelLikeKing22";

    public static String USER_NAME = "root";

    public static Connection getConnection()  {
        try {
            Class.forName(Load_Driver);
            connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
        }
        catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());;
        }

        System.out.println("Connection Established...........");
        return connection;

    }
    public  static void closeConnection()
    {
        if (connection != null)
        {
            System.out.println("Database connection established successfully");
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                System.out.println("Database connection error: " + e.getMessage());
            }

        }

    }
}

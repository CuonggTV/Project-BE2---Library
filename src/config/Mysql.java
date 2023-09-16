package config;

import java.sql.*;


public class Mysql {
    private static String connectURL = "jdbc:mysql://localhost:3306/testlibrary";
    public  static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(connectURL, "root", "cuong12345");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
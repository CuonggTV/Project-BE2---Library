package config;

import java.sql.*;

//public class Mysql {
//    private static String connectURL = "jdbc:mysql://localhost:3306/testlibrary";
//    public  static Connection connection;
//
//    public static void getConnected(){
//        try {
//            connection = DriverManager.getConnection(connectURL, "root", "123123Red");
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }



//}

public class Mysql {
    private static String connectURL = "jdbc:mysql://localhost:3306/testlibrary";
    private static Connection connection;
    private static String username = "root";
    private static String password = "cuong12345";

    public  static Connection connection;

    public static Connection getConnected() {
        try {
            connection = DriverManager.getConnection(connectURL, "root", "123123Red");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(connectURL, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
package config;

import java.sql.*;


//}

public class Mysql {
    private static Connection connection;

    public static Connection getConnected() {
        try {
            connection = DriverManager.getConnection(DBSettings.connectURL, DBSettings.username,DBSettings.password);
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
            connection = DriverManager.getConnection(DBSettings.connectURL, DBSettings.username,DBSettings.password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
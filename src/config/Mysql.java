package config;

import java.sql.*;


//}

public class Mysql {
    public static Connection connection;
    public static Statement statement;

    public Mysql()  {
    }

    public static Connection getConnected() {
        try {
            connection = DriverManager.getConnection(DBSettings.connectURL, DBSettings.username,DBSettings.password);
            statement = Mysql.connection.createStatement();
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
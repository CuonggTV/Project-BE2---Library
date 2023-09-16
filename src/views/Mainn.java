package views;

import config.Mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mainn {
    public static void main(String[] args) throws SQLException {
        Statement statement = Mysql.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from people");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("firstname"));
        }
        Mysql.connection.close();
    }
}

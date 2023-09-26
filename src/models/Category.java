package models;

import config.Mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Category {
    public static void createNewBookInCategory(int bookID) throws SQLException{
        String sql = "INSERT IGNORE INTO bookcategories(bookid) values (" + bookID + ");";
        Mysql.statement.executeUpdate(sql);
    }
    public static boolean checkBooKCategory(int bookID) throws SQLException {
        String sql = "SELECT COUNT(1) FROM bookcategories WHERE bookid = " + bookID;
        ResultSet resultSet = Mysql.statement.executeQuery(sql);
        while (resultSet.next()) {
            if(resultSet.getInt("COUNT(1)") ==0) return false;
        }
        return true;
    }
    public static void getListBookCategory(int bookID, List<String> bookCategories, List<String> categories) throws SQLException {
        String sql = "SELECT * FROM bookcategories where bookID = " + bookID;
        ResultSet resultSet = Mysql.statement.executeQuery(sql);
        while (resultSet.next()) {
            for (int i = 1;i <categories.size();i++){
                if(resultSet.getBoolean(categories.get(i))){
                    bookCategories.add(categories.get(i));
                }
            }

        }
    }
    public static void getCategories(List<String> categories) throws SQLException {
        String sqlString = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = 'bookcategories' and table_schema = 'librarydb' order by ORDINAL_POSITION;";
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while(resultSet.next()){
            categories.add(resultSet.getString("COLUMN_NAME"));
        }
    }
}

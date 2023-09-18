package views;

import config.BookList;
import config.Mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mainn {
    public static void main(String[] args) throws SQLException {

        BookList books = new BookList();
        books.addBook();
    }
}

package views;

import config.Mysql;
import models.BookList;

import java.sql.Connection;
import java.sql.SQLException;

public class Mainn {
    public static void main(String[] args) throws SQLException {

        Mysql.getConnected();
        BookList books = new BookList();
//        books.addBook();
        books.showAllBooksWithTheirStatus();


    }
}

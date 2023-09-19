package views;

import models.BookList;

import java.sql.SQLException;

public class Mainn {
    public static void main(String[] args) throws SQLException {

        BookList books = new BookList();
//        books.addBook();
        books.showAllBooksWithTheirStatus();
    }
}

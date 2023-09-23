package views;

import config.Mysql;
import models.BookList;

import java.sql.SQLException;

public class Mainn {
    public static void main(String[] args) throws SQLException {

        Mysql.getConnected();
        BookView bookView = new BookView();
//        bookView.showBorrowedBook();
        bookView.deleteBook();
    }
}

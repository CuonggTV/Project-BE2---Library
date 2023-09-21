package views;

import config.Mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookView {
    public void showBorrowedBook() {
        String sql = "SELECT l.id, b.bookName, l.loanDate, l.returnDate FROM loan l JOIN book b ON l.bookID = b.id;";
        try {
            ResultSet resultSet = Mysql.statement.executeQuery(sql);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String bookName = resultSet.getString("bookName");
                String loanDate = resultSet.getString("loanDate");
                String returnDate = resultSet.getString("returnDate");

                System.out.printf("%s\t\t\t%s\t\t\t%s\t\t\t%s%n", id, bookName, loanDate, returnDate);
            }
        } catch (SQLException e) {
            // Handle the exception or display an error message
            e.printStackTrace();
        }
    }
}

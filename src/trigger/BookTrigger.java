package trigger;

import config.Mysql;

import java.sql.SQLException;

public class BookTrigger {
    public static void insertFineTrigger(int bookID) throws SQLException {
        String sql = "UPDATE book SET copiesOwned = copiesOwned - 1" +
                " where id = " + bookID;
        Mysql.statement.executeUpdate(sql);
    }
    public static void payLoanTrigger(int bookID) throws SQLException {
        String sql = "UPDATE book SET copiesOwned = copiesOwned + 1" +
                " where id = " + bookID;
        Mysql.statement.executeUpdate(sql);
        addBookTrigger(bookID);
    }
    public static void addBookTrigger(int bookID) throws SQLException {
        String sql = "UPDATE reservation SET status = 1 " +
                " where bookID = " + bookID;
        Mysql.statement.executeUpdate(sql);
    }
}

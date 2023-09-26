package models;

import config.Mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Loan {
    private int id;
    private int customerID;
    private int bookID;
    private String loanDate;
    private String returnDate;
    private boolean status;
    public Loan() {
    }
    public Loan(int id, int customerID, int bookID, String loanDate, String returnDate, boolean status) {
        this.id = id;
        this.customerID = customerID;
        this.bookID = bookID;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public void setLoanByID(int loanID) throws SQLException {
        String sqlString = "select * from loan where id = "+ loanID ;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while (resultSet.next()) {
            id = resultSet.getInt("id");
            customerID = resultSet.getInt("customerID");
            bookID = resultSet.getInt("bookID");
            loanDate = resultSet.getString("loanDate");
            returnDate = resultSet.getString("returnDate");
            status = resultSet.getBoolean("status");
        }
    }
    public static int getBookID_By_LoanID(int loanID) throws SQLException {
        String sqlString = "select bookID from book where id = "+ loanID ;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);
        int bookID = 0;
        while (resultSet.next()) {
            bookID = resultSet.getInt("bookID");
        }
        return  bookID;
    }
    public static boolean checkLoan(int loanID) throws SQLException {
        String sqlString = "SELECT COUNT(1) FROM loan WHERE id = " +loanID + " and status = 0";
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);
        while (resultSet.next()) {
            if(resultSet.getInt("COUNT(1)") ==0) return false;
        }
        return true;
    }
}

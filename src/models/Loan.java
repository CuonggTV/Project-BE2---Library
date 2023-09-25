package models;

import config.Mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

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

}

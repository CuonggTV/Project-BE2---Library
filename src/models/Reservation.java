package models;

import config.Mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Reservation {
    private int id;
    private int customerID;
    private int bookID;
    private String dateCreated;
    private boolean status;

    public Reservation() {
    }

    public Reservation(int id, int customerID, int bookID, String dateCreated, boolean status) {
        this.id = id;
        this.customerID = customerID;
        this.bookID = bookID;
        this.dateCreated = dateCreated;
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
        return dateCreated;
    }

    public void setLoanDate(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setReservationByID(int reservationID) throws SQLException {
        String sqlString = "select * from reservation where id = "+ reservationID ;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while (resultSet.next()) {
            id = resultSet.getInt("id");
            customerID = resultSet.getInt("customerID");
            bookID = resultSet.getInt("bookID");
            status = resultSet.getBoolean("status");
            dateCreated = resultSet.getString("dateCreated");
        }
    }
}

package models;

import config.Mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Fine {
    private int id;
    private int customerID;
    private int loanID;
    private String deadline;
    private float amount;

    public Fine() {
    }

    public Fine(int id, int customerID, int loanID, String deadline, float amount) {
        this.id = id;
        this.customerID = customerID;
        this.loanID = loanID;
        this.deadline = deadline;
        this.amount = amount;
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

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setFineByID(int fineID) throws SQLException {
        String sqlString = "select * from fine where id = "+ fineID;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while (resultSet.next()) {
            id = resultSet.getInt("id");
            customerID = resultSet.getInt("customerID");
            loanID = resultSet.getInt("loanID");
            deadline = resultSet.getString("deadline");
            amount = resultSet.getFloat("amount");
        }
    }

    public static boolean checkFine1111(int customerID) throws SQLException {
        String sqlString = "select id from fine where customerID = " +customerID;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);
        while(resultSet.next()){
            return true;
        }
        return false;
    }

    public static boolean checkFine(int customerID) throws SQLException {
        String sqlString = "SELECT COUNT(1) FROM fine WHERE customerID = " +customerID;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);
        while (resultSet.next()) {
            if(resultSet.getInt("COUNT(1)") ==0) return false;
        }
        return true;
    }
}

package models;

import config.Mysql;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    private int id;
    private String userName;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private float balance;


    public Customer() {
    }

    public Customer(int id) {
        this.id = id;
    }


    public Customer(String userName, String password, String firstName, String middleName,String lastName, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void getCustomerByID(int customerID) throws SQLException {
        String sqlString = "select * from customer where id = "+ customerID ;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while (resultSet.next()) {
            id = resultSet.getInt("id");
            userName = resultSet.getString("username");
            password = resultSet.getString("password");
            firstName = resultSet.getString("firstname");
            middleName = resultSet.getString("middlename");
            lastName = resultSet.getString("lastname");
            phoneNumber = resultSet.getString("phonenumber");

            balance = resultSet.getFloat("balance");
        }

    }


}

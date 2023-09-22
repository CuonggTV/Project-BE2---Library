package services;

import config.Mysql;
import models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerServices{
    public Customer getCustomer(String username, String password) throws SQLException {
        String sqlString = "select * from customer where username = "+username+" and password = " +password;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        Customer customer = new Customer();
        while (resultSet.next()) {
            customer.setId(resultSet.getInt("id"));
            customer.setUserName(resultSet.getString("username"));
            customer.setPassword(resultSet.getString("password"));
            customer.setFirstName(resultSet.getString("firstname"));
            customer.setMiddleName(resultSet.getString("middlename"));
            customer.setLastName(resultSet.getString("lastname"));
            customer.setPhoneNumber(resultSet.getString("phonenumber"));
        }
        return customer;
    }

    public void add(Customer customer) throws SQLException {
        String sqlString = "insert into customer(username,password,firstname,middlename,lastname,phonenumber) values(\""+
                customer.getUserName() +"\",\"" +
                customer.getPassword() +"\",\"" +
                customer.getFirstName() + "\",\"" +
                customer.getMiddleName() + "\",\"" +
                customer.getLastName() + "\",\"" +
                customer.getPhoneNumber() +"\");";
        System.out.println(sqlString);
        Mysql.statement.executeUpdate(sqlString);
    }


    public void add() throws SQLException {

    }


    public void read() {

    }


    public void update() {

    }

    public void delete(int id) throws SQLException {
        String sqlString = "DELETE FROM customer WHERE id = " + id;
        Mysql.statement.executeQuery(sqlString);
    }

}

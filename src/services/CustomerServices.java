package services;

import config.Mysql;
import models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerServices {
    public Customer getCustomer(String username, String password) throws SQLException {
        String sqlString = "select * from customer where username = "+username+" and password = " +password;
        Statement statement = Mysql.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlString);


        Customer customer = new Customer();
        while (resultSet.next()) {
            customer.getFirstName(resultSet.getString("firstname"));
            customer.getMiddleName(resultSet.getString("middlename"));
        }
        Mysql.connection.close();
        return  customer;
    }

    public void deleteCustomer(String username, String password) throws SQLException {
        String sqlString = "select * from customer where username = "+username+" and password = " +password;
        Statement statement = Mysql.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlString);

    }
}

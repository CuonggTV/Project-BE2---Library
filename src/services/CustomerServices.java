package services;

import config.Mysql;
import models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerServices implements IService {
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
    @Override
    public void add(Object o) throws SQLException {
//        Customer customer = (Customer) o;
//        String sqlString = 'insert into customer(username,password,firstname,middlename,lastname,phonenumber)
//        values("","12345","Tran","Van","Cuong","0123456789");';
//        Mysql.statement.executeQuery(sqlString);

    }

    @Override
    public void read() {

    }

    @Override
    public void update() {

    }

    public void delete(int id) throws SQLException {
        String sqlString = "DELETE FROM customer WHERE id = " + id;
        Mysql.statement.executeQuery(sqlString);
    }

}

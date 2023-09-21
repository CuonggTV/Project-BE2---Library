package views;

import config.Mysql;
import models.Customer;
import utils.OperationHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerView implements IView{
    @Override
    public void showInfo(Object ob) {
        Customer customer = (Customer) ob;
        System.out.println("Username: "+customer.getUserName());
        System.out.println("Name: "+customer.getFirstName() +" "+customer.getMiddleName()+" "+customer.getLastName());
        System.out.println("Phonenumber: "+customer.getPhoneNumber());
    }

//    @Override
//    public void showAllInfo() throws SQLException {
//        String sqlString = "select * from customer";
//        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);
//
//        while (resultSet.next()) {
//
//            System.out.println("Id: "+resultSet.getString("id"));
//
//            System.out.println("Username: "+resultSet.getString("username"));
//            System.out.println("Name: "+
//                    resultSet.getString("firstname") +" "+
//                    resultSet.getString("middlename")+" "+
//                    resultSet.getString("lastname")
//            );
//            System.out.println(resultSet.getString("phonenumber"));
//
//            System.out.println();
//        }
//    }

    @Override
    //Also use as login
    public Object inputInfo() throws SQLException {
        String username;
        String password;
        String firstName;
        String middleName;
        String lastName;
        String phoneNumber;
        ResultSet resultSet;

        do {
            username = OperationHelper.inputString("Enter your username: ");
            String sqlString = "select * from customer where username = "+ username;
            resultSet = Mysql.statement.executeQuery(sqlString);
        }while (resultSet.next());

        password = OperationHelper.inputString("Enter your password: ");
        firstName = OperationHelper.inputString("Enter your first name: ");
        middleName = OperationHelper.inputString("Enter your middle name: ");
        lastName = OperationHelper.inputString("Enter your last name: ");
        phoneNumber = OperationHelper.inputString("Enter your phone number: ");

        return new Customer(username,password,firstName,middleName,lastName,phoneNumber) ;
    }
}

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

    public void showAllInfo() throws SQLException {
        String sqlString = "select * from customer";
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while (resultSet.next()) {

            System.out.println("Id: "+resultSet.getString("id"));

            System.out.println("Username: "+resultSet.getString("username"));
            System.out.println("Name: "+
                    resultSet.getString("firstname") +" "+
                    resultSet.getString("middlename")+" "+
                    resultSet.getString("lastname")
            );
            System.out.println(resultSet.getString("phonenumber"));

            System.out.println();
        }
    }

    public int login() throws SQLException {
        String username, password;
        ResultSet resultSet;

            username = OperationHelper.inputString("Enter your username: ");
            password = OperationHelper.inputString("Enter your password: ");

            String sqlString = "select * from customer where username = \""+ username+"\" AND password = \"" + password +"\"";
            resultSet = Mysql.statement.executeQuery(sqlString);

            if (!resultSet.next()){
                System.out.println("This account is not register.");
                return 0;
            }

          return resultSet.getInt("id");
    }

    @Override
    //Also use as login
    public Customer inputInfo() throws SQLException {
        String username;
        String password;
        String firstName;
        String middleName;
        String lastName;
        String phoneNumber;
        ResultSet resultSet;

        while (true){
            username = OperationHelper.inputString("Enter your username: ");
            String sqlString = "select * from customer where username = \""+ username+"\"";
            resultSet = Mysql.statement.executeQuery(sqlString);

            if (resultSet.next()){
                System.out.println("This username has been used");
                continue;
            }
            break;
        }

        password = OperationHelper.inputString("Enter your password: ");
        firstName = OperationHelper.inputString("Enter your first name: ");
        middleName = OperationHelper.inputString("Enter your middle name: ");
        lastName = OperationHelper.inputString("Enter your last name: ");
        phoneNumber = OperationHelper.inputString("Enter your phone number: ");

        return new Customer(username,password,firstName,middleName,lastName,phoneNumber) ;
    }

    public void showBorrowedBooks(int customerID) throws SQLException {
        String sqlString = "select book.bookName, book.author, loan.returnDate " +
                "from book, loan " +
                "where loan.bookID = book.id And loan.customerID = " +customerID;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while(resultSet.next()){
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Book: \""+resultSet.getString("bookName")
                    +"\" written by: " + resultSet.getString("author") );
            System.out.println("Return date" + resultSet.getString("returnDate"));
            System.out.println("-----------------------------------------------------------------\n");
        }
    }

    public void showOwnFine(int customerID) throws SQLException {
        String sqlString = "select book.bookName, fine.amount, fine.deadline " +
                "from fine " +
                "INNER JOIN loan on fine.loanID = loan.id " +
                "INNER JOIN book on loan.bookID = book.id " +
                "where fine.customerID = " + customerID;

        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while(resultSet.next()){
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Fine from returning \""+resultSet.getString("bookName") + "\" late");
            System.out.println("    Need to pay: " + resultSet.getString("amount") + " $");
            System.out.println("    Deadline: " + resultSet.getString("deadline"));
            System.out.println("-----------------------------------------------------------------\n");
        }
    }
}

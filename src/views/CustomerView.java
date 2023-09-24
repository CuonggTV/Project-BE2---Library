package views;

import config.Mysql;
import models.Customer;
import utils.OperationHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerView implements IView{
    @Override
    public void showInfo(Object ob) {
        Customer customer = (Customer) ob;
        System.out.println("Username: "+customer.getUserName());
        System.out.println("Name: "+customer.getFirstName() +" "+customer.getMiddleName()+" "+customer.getLastName());
        System.out.println("Phone number: "+customer.getPhoneNumber());
    }

    public void showAllInfo(int id) throws SQLException {
        String sqlString = "select * from customer where id = " + id;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while (resultSet.next()) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Id: "+resultSet.getString("id"));

            System.out.println("Username: "+resultSet.getString("username"));
            System.out.println("Name: "+
                    resultSet.getString("firstname") +" "+
                    resultSet.getString("middlename")+" "+
                    resultSet.getString("lastname")
            );
            System.out.println("Phone number: " +resultSet.getString("phonenumber"));
            System.out.println("Balance: " + resultSet.getString("balance"));
            System.out.println("----------------------------------------------------------");
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

    public String inputUsername() throws SQLException {
        String username = null;
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
        return username;
    }
    public String inputPhoneNumber() {
        String phoneNumber = null;

        while (true){
            phoneNumber = OperationHelper.inputString("Enter your phoneNumber: ");

            if(phoneNumber.length()==10) {
                break;
            }
            else if (OperationHelper.isNumeric(phoneNumber,"")){
                System.out.println("Phone number must have 10 number");
                continue;
            }
            break;
        }
        return phoneNumber;
    }



    @Override
    //Also use as login
    public Customer inputInfo() throws SQLException {
        String username = inputUsername();
        String password = OperationHelper.inputString("Enter your password: ");
        String firstName = OperationHelper.inputString("Enter your first name: ");
        String middleName = OperationHelper.inputString("Enter your middle name: ");
        String lastName = OperationHelper.inputString("Enter your last name: ");
        String phoneNumber = inputPhoneNumber();

        return new Customer(username,password,firstName,middleName,lastName,phoneNumber) ;
    }

    public void showBorrowedBooks(int customerID) throws SQLException {
        String sqlString = "select loan.id, book.bookName, book.author, loan.returnDate " +
                "from book, loan " +
                "where loan.bookID = book.id And loan.customerID = " +customerID +
                " and status = 0";
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while(resultSet.next()){
            System.out.println("-----------------------------------------------------------------");
            System.out.println("LoanID: " +resultSet.getInt("loan.id") );
            System.out.println("    Book: \""+resultSet.getString("bookName")
                    +"\" written by: " + resultSet.getString("author") );
            System.out.println("    Return date: " + resultSet.getString("returnDate"));
            System.out.println("-----------------------------------------------------------------\n");
        }
    }

    public void showOwnFine(int customerID) throws SQLException {
        String sqlString = "select fine.loanID, book.bookName, fine.amount, fine.deadline " +
                "from fine " +
                "INNER JOIN loan on fine.loanID = loan.id " +
                "INNER JOIN book on loan.bookID = book.id " +
                "where fine.customerID = " + customerID;

        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while(resultSet.next()){
            System.out.println("-----------------------------------------------------------------");
            System.out.print(resultSet.getInt("loanID")+". ");
            System.out.println("Fine from returning \""+resultSet.getString("bookName") + "\" late");
            System.out.println("    Need to pay: " + resultSet.getString("amount") + " $");
            System.out.println("    Deadline: " + resultSet.getString("deadline"));
            System.out.println("-----------------------------------------------------------------\n");
        }
    }

    public void showCategories(List<String> categories){
        for(int i= 1;i<categories.size();i++){
            System.out.println(i+". "+categories.get(i));
        }
    }
    public String[] inputChoice(String mess){
        String input = OperationHelper.inputString(mess);
        return input.split(" ");
        //return OperationHelper.isArrayOfInteger(choice, "Wrong input format") || choice[0].equals("none");
    }
    public void showBookByCategories(String sqlString) throws SQLException {
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        System.out.println("-----------------------------------------------------------------\n");
        while(resultSet.next()){
            System.out.print(resultSet.getString("ID") +". ");
            System.out.println("\"" +resultSet.getString("bookName") +"\""+ " written by " + "resultSet.getString(\"bookName\")" );
        }
        System.out.println("-----------------------------------------------------------------\n");

    }

    public boolean showResultBorrowBook(float totalmoney){
        System.out.println("Your return date will be " + OperationHelper.DateString(1));
        System.out.println("Total money: " + totalmoney);
        if(OperationHelper.confirm("Confirm ?")){
            return true;
        }
        return false;
    }

    public boolean showNotEnoughCopies(String bookName){
        System.out.println("There is no \""+bookName+ "\" left in the library!");
        if(OperationHelper.confirm("Do you want to make a reservation ?")){
            return true;
        }
        return false;
    }

    public int showUpdateInfoMenu(){
        System.out.println("1. Update username.");
        System.out.println("2. Update password.");
        System.out.println("3. Update phone number.");
        System.out.println("4. Add balance.");
        System.out.println("5. Out.");
        return OperationHelper.inputIntegerWithRange("Your choice: ",1,5);
    }

    public void showReservation(int id) throws SQLException {
        String sql = "SELECT book.bookName, rs.dateCreated, rs.status from reservation as rs, book " +
                "where rs.customerID = " +id +" and book.id = rs.bookID;" ;
        ResultSet resultSet = Mysql.statement.executeQuery(sql);

        while (resultSet.next()) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Book name: "+resultSet.getString("bookName"));
            System.out.println("Date created: " +resultSet.getString("dateCreated"));
            if(resultSet.getBoolean("status")){
                System.out.println("Status: is in the library");
            }
            else System.out.println("Status: still not in library");
            System.out.println("----------------------------------------------------------");
        }
    }


}

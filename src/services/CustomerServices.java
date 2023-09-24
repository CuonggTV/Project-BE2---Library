package services;

import config.Mysql;
import models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    public void delete(int id) throws SQLException {
        String sqlString = "DELETE FROM customer WHERE id = " + id;
        Mysql.statement.executeQuery(sqlString);
    }

    public void updateUsername(int id, String newUsername) throws SQLException {
        String sqlString = "Update customer set username = " + newUsername + " where id = " +id;;
        Mysql.statement.executeUpdate(sqlString);
    }

    public void updatePassword(int id, String newPassword) throws SQLException {
        String sqlString = "Update customer set password = " + newPassword + " where id = " +id;;
        Mysql.statement.executeUpdate(sqlString);
    }

    public void updatePhoneNumber(int id, String phoneNumber) throws SQLException {
        String sqlString = "Update customer set phoneNumber = " + phoneNumber + " where id = " +id;;
        Mysql.statement.executeUpdate(sqlString);
    }

    public void addBalance(int id, float balance) throws SQLException {
        String sqlString = "Update customer set balance = " + balance + " where id = " +id;;
        Mysql.statement.executeUpdate(sqlString);
    }

//    public void updateUsername(int id, String newUsername) throws SQLException {
//        String sqlString = "Update customer set username = " + newUsername + " where id = " +id;;
//        Mysql.statement.executeUpdate(sqlString);
//    }
//
//    public void updateUsername(int id, String newUsername) throws SQLException {
//        String sqlString = "Update customer set username = " + newUsername + " where id = " +id;;
//        Mysql.statement.executeUpdate(sqlString);
//    }

    public void getCategories(List<String> categories) throws SQLException {
        String sqlString = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = 'bookcategories' and table_schema = 'librarydb' order by ORDINAL_POSITION;";
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        while(resultSet.next()){
            categories.add(resultSet.getString("COLUMN_NAME"));
        }
    }
    public String turnChoicesToSQL(String []choice, List<String> categories){
        StringBuilder sqlString = new StringBuilder("select book.id, book.bookName , book.author from book, bookcategories " +
                "where book.id = bookcategories.bookID and ");

        for(int i =0;i<choice.length;i++){
            try {
                sqlString.append("`").append(categories.get(Integer.parseInt(choice[i]))).append("` = 1 ");
            }
            catch (Exception e){
                System.out.println(e);
                System.out.println("This is not in categories");
                return null;
            }
            if (i!= choice.length-1){
                sqlString.append("and ");
            }
        }
        return sqlString.toString();
    }

    public boolean checkBook(int id) throws SQLException {
        //Xac nhan co id nay chua
        String sql = "SELECT COUNT(1) FROM book WHERE id = " + id;
        ResultSet resultSet = Mysql.statement.executeQuery(sql);
        while (resultSet.next()) {
            if(resultSet.getInt("COUNT(1)") ==0) return false;
        }
        return true;
    }

    public float getBorrowedFee(int bookID) throws SQLException {
        String sql = "SELECT borrowedFee FROM book WHERE id = " + bookID;
        ResultSet resultSet = Mysql.statement.executeQuery(sql);

        float borrowedFee = 0;
        while (resultSet.next()) {
            borrowedFee = resultSet.getFloat("borrowedFee") ;
        }
        return borrowedFee;
    }

    public String getBookName(int bookID) throws SQLException {
        String sql = "SELECT bookName FROM book WHERE id = " + bookID;
        ResultSet resultSet = Mysql.statement.executeQuery(sql);

        String bookName = null;
        while (resultSet.next()) {
            bookName = resultSet.getString("bookName");
        }
        return bookName;
    }

    public void createLoan(int customerID, int bookID) throws SQLException {
        String sqlString = "insert loan (bookID,customerID,loanDate, returnDate) " +
                "values(" + bookID + "," + customerID + "," +
                "CURRENT_DATE(), DATE_ADD(CURRENT_DATE(), INTERVAL 14 day));";
        Mysql.statement.executeUpdate(sqlString);
    }

    public void createReservation(int customerID, int bookID) throws SQLException {
        String sqlString = "insert reservation (bookID,customerID,status, dateCreated) " +
                "values(" + bookID + "," + customerID + "," +
                "0, CURRENT_DATE());";
        Mysql.statement.executeUpdate(sqlString);
    }

    public boolean subtractBalance(int customerID, float totalMoney) throws SQLException {
        String sqlString = "SELECT balance FROM customer WHERE id = " + customerID;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        float balance = 0;
        while (resultSet.next()){
           balance = resultSet.getFloat("balance");
        }

        if(balance-totalMoney < 0) return false;
        else{
            balance -= totalMoney;
            sqlString = "Update customer set balance = " + balance + " where id = " +customerID;
            Mysql.statement.executeUpdate(sqlString);
        }
        return true;
    }

    public boolean subtractCopiesOwned(int bookID) throws SQLException {
        String sqlString = "SELECT copiesOwned FROM book WHERE id = " + bookID;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        int copiesOwned = 0;
        while (resultSet.next()){
            copiesOwned = resultSet.getInt("copiesOwned");
        }
        if(copiesOwned -1 == 0) return false;
        else{
            copiesOwned--;
            sqlString = "Update book set copiesOwned = " + copiesOwned + " where id = " +bookID;
            Mysql.statement.executeUpdate(sqlString);
        }
        return true;
    }

    public void createFine() throws SQLException {
        String sqlString = "select id, customerID, bookD from loan where curdate() > returnDate;";
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        List<Integer> loan = new ArrayList<>();

        while (resultSet.next()){
            float fee =getBorrowedFee(resultSet.getInt("bookID"));
            fee += fee*0.1;
            sqlString = "INSERT into fine(customerID,loanID,deadline,amount) VALUES(" +
                    resultSet.getInt("customerID")+"," +
                    resultSet.getInt("id") +","+
                    "curdate(),"+fee+");";
            Mysql.statement.executeUpdate(sqlString);
        }
    }

    public void disableLoan(int customerID , int loanID) throws SQLException {
        String sqlString = "Update loan set status = " + 1 + " where id = " +loanID +" and "+"customerID = "+customerID;;
        Mysql.statement.executeUpdate(sqlString);
    }

    public void deleteFine(int customerID,int fineID) throws SQLException {
        String sqlString = "select amount " +
                "from fine " +
                "where id = " + fineID +" and customerID = " + customerID;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);
        float amount = 0;

        while(resultSet.next()){
            amount = resultSet.getFloat("amount");
        }
        if(amount == 0){
            System.out.println("You enter wrong fine!");
        }
        //Trừ vảo tk
        if(subtractBalance(customerID,amount)){
            sqlString = "DELETE FROM fine WHERE customerID = " + customerID + " and id = "+fineID;
            Mysql.statement.executeUpdate(sqlString);
        }
        else{
            System.out.println("Your account doesn't have enough money.");
        }
    }
}

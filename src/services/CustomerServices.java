package services;

import config.Mysql;
import models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
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
        String sqlString = "insert into customer(username,password,firstname,middlename,lastname,phonenumber,balance) values(\""+
                customer.getUserName() +"\",\"" +
                customer.getPassword() +"\",\"" +
                customer.getFirstName() + "\",\"" +
                customer.getMiddleName() + "\",\"" +
                customer.getLastName() + "\",\"" +
                customer.getPhoneNumber() +"\",\"" +
                customer.getBalance() +"\");";
        System.out.println(sqlString);
        Mysql.statement.executeUpdate(sqlString);
    }

    public float getFineAmount(int customerID,int loanID) throws SQLException {
        String sqlString = "SELECT amount from fine where loanID = " + loanID + " and customerID = "+ customerID;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);
        float amount = 0;
        while (resultSet.next()){
            amount = resultSet.getFloat("amount");
        }
        return amount;
    }

    public boolean checkLoan(int loanID) throws SQLException {
        String sql = "SELECT COUNT(1) FROM loan WHERE id = " + loanID;
        ResultSet resultSet = Mysql.statement.executeQuery(sql);
        while (resultSet.next()) {
            if(resultSet.getInt("COUNT(1)") ==0) return false;
        }
        return true;
    }


    public void delete(int id) throws SQLException {
        String sqlString = "DELETE FROM customer WHERE id = " + id;
        Mysql.statement.executeQuery(sqlString);
    }

    public void updateUsername(int id, String newUsername) throws SQLException {
        String sqlString = "UPDATE customer SET username = '" + newUsername + "' WHERE id = " + id;
        Mysql.statement.executeUpdate(sqlString);
    }

    public void updatePassword(int id, String newPassword) throws SQLException {
        String sqlString = "Update customer set password = '" + newPassword + "' where id = " +id;
        Mysql.statement.executeUpdate(sqlString);
    }

    public void updatePhoneNumber(int id, String phoneNumber) throws SQLException {
        String sqlString = "Update customer set phoneNumber = " + phoneNumber + " where id = " +id;
        Mysql.statement.executeUpdate(sqlString);
    }

    public void addBalance(int id, float balance) throws SQLException {
        String sqlString = "Update customer set balance = " + balance + " where id = " +id;
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

    public static void getCategories(List<String> categories) throws SQLException {
        String sqlString = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = 'bookcategories' and table_schema = 'testlibrary' order by ORDINAL_POSITION;";
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

    public static float getBorrowedFee(int bookID) throws SQLException {
        String sql = "SELECT borrowedFee FROM book WHERE id = " + bookID;
        ResultSet resultSet = Mysql.statement.executeQuery(sql);

        float borrowedFee = 0;
        while (resultSet.next()) {
            borrowedFee = resultSet.getFloat("borrowedFee") ;
        }
        return borrowedFee;
    }

    public static  String getBookName(int bookID) throws SQLException {
        String sql = "SELECT bookName FROM book WHERE id = " + bookID;
        ResultSet resultSet = Mysql.statement.executeQuery(sql);

        String bookName = null;
        while (resultSet.next()) {
            bookName = resultSet.getString("bookName");
        }
        return bookName;
    }

    public void createLoan(int customerID, int bookID) throws SQLException {
        String sqlString = "insert loan (bookID,customerID,loanDate, returnDate,status) " +
                "values(" + bookID + "," + customerID + "," +
                "CURRENT_DATE(), DATE_ADD(CURRENT_DATE(), INTERVAL 14 day),0);";
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

    public static boolean checkFine(int loanID) throws SQLException {
        String sqlString = "select id from fine where loanID = " +loanID;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);
        while(resultSet.next()){
            return true;
        }
        return false;
    }
    public static void createFine(int customerID, int loanID, float fee) throws SQLException {
        String sqlString = "INSERT into fine(customerID,loanID,deadline,amount) VALUES(" +
                customerID+"," +
                loanID +","+
                "curdate(),"+fee+");";
        Mysql.statement.executeUpdate(sqlString);

    }

    public static boolean getFine(int loanID) throws SQLException {
        String sqlString = "SELECT * from fine where loanid = " + loanID;
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);
        while(resultSet.next()){
            return true;
        }
        return false;
    }

    public static void checkAndCreateFine() throws SQLException {
        String sqlString = "select loan.id, loan.customerID, book.borrowedFee from loan, book " +
                "where curdate() > loan.returnDate and loan.status = 0 " +
                "and loan.bookID = book.id";
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        List<Integer> customerIDList = new ArrayList<>();
        List<Integer> loanIDList = new ArrayList<>();
        List<Float> feeList = new ArrayList<>();

        while (resultSet.next()){
            int customerID = resultSet.getInt("loan.customerID");
            int loanID = resultSet.getInt("loan.id");
            float fee = resultSet.getFloat("book.borrowedFee");
            fee += fee*0.1;
            customerIDList.add(customerID);
            loanIDList.add(loanID);
            feeList.add(fee);
        }
        resultSet.close();
        //Xoa da co
        for(int i =0;i<customerIDList.size();i++){
            if(getFine(loanIDList.get(i))){
                loanIDList.remove(i);
                customerIDList.remove(i);
                feeList.remove(i);
                i--;
            }
        }
        for(int i =0;i<customerIDList.size();i++){
            createFine(customerIDList.get(i),loanIDList.get(i),feeList.get(i));
        }
    }

    public void disableLoan(int customerID , int loanID) throws SQLException {
        String sqlString = "Update loan set status = " + 1 + " where id = " +loanID +" and "+"customerID = "+customerID;;
        Mysql.statement.executeUpdate(sqlString);
    }

    public boolean deleteFine(int customerID,int loanID) throws SQLException {
        //Trừ vảo tk
            String sqlString = "DELETE FROM fine WHERE customerID = " + customerID + " and loanID = "+loanID;
            int result = Mysql.statement.executeUpdate(sqlString);
            if(result != 0){
                return true;
            }
            return false;
    }

    public static void updateFine(int id, int amount) throws SQLException {
        String sql = "Update fine set amount = " + amount + ", deadline = curdate() " +" where id = "+id;
        Mysql.statement.executeUpdate(sql);
    }

    public static void upToDateFine() throws SQLException {
        String sqlString = "select id, amount from fine " +
                "where curdate() > deadline;";
        ResultSet resultSet = Mysql.statement.executeQuery(sqlString);

        List<Integer> idList = new ArrayList<>();
        List<Integer> amountList = new ArrayList<>();

        while (resultSet.next()){
            int id = resultSet.getInt("id");
            int amount = resultSet.getInt("amount");
            amount += amount*0.1;
            idList.add(id);
            amountList.add(amount);
        }
        resultSet.close();
        for(int i =0;i<idList.size();i++){
            updateFine(idList.get(i),amountList.get(i));
        }
    }


}

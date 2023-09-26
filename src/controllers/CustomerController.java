package controllers;

import config.Mysql;
import models.*;
import services.CustomerServices;
import trigger.BookTrigger;
import utils.OperationHelper;
import views.AdminView;
import views.CustomerView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    public CustomerView view;
    public CustomerServices services;



    public CustomerController() {
        view = new CustomerView();
        services = new CustomerServices();
    }
    public CustomerController(CustomerServices customerServices) {
        this.services = customerServices;
    }
    public CustomerController(CustomerView customerView) {
        this.view = customerView;
    }
    public CustomerController(CustomerView customerView, CustomerServices customerServices) {
        this.view = customerView;
        this.services = customerServices;
    }
//    public void showAllStudent() throws SQLException {
//        this.customerView.showAllInfo();
//    }

    public int login() throws SQLException {
        int choice;
        do {
            int id = view.login();
            if(id>0){
                return id;
            }
            choice = OperationHelper.inputIntegerWithRange("Continue?\n1. Yes   2. No",1,2);
            if(choice==2) break;
        }while (choice ==1);
        return 0;
    }
    public void register() throws SQLException {
        Customer customer = view.inputInfo();
        services.add(customer);
    }

    public void payFine(int id) throws SQLException {
        FineList fineList = new FineList();
        fineList.getFineList(id);
        if (fineList.isEmpty()) {
            System.out.println("You don't have any fine.");
            return;
        }
        fineList.showList();
        String[] choice = view.inputChoice("Enter your loanID to pay: ");
        for (int i = 0; i < choice.length; i++) {
            int loanID = Integer.parseInt(choice[0]);
            //Check có trong fine của mình ko
            boolean inList = false;
            for (int j = 0; j < fineList.size(); j++) {
                if (loanID == fineList.get(j).getLoanID()) {
                    inList = true;
                    //Phải trả loan trước
                    if(Loan.checkLoan(loanID)){
                        System.out.println(
                                "Return book \"" + Book.getBookNameByID(Loan.getBookID_By_LoanID(loanID)) +
                                "\" with loanID(" +loanID +") first!"
                        );
                        break;
                    }
                    if (!services.subtractBalance(id, services.getFineAmount(id, loanID))) {
                        System.out.println("Your account does not have enough money.");
                        return;
                    }
                    if (services.deleteFine(id, loanID)) {
                        System.out.println("Fine with loanID(" + loanID + ") has been paid!");
                        fineList.remove(j);
                        break;
                    }
                }
            }
            if (!inList) System.out.println("Fine with loanID(" + loanID + ") does not exist!");
        }
    }

    public void payLoan(int id) throws SQLException {
        LoanList loanList = new LoanList();
        loanList.getLoanList(id);

        if(loanList.isEmpty()) {
            System.out.println("You don't have any loan.");
            return;
        }
        loanList.showList();
        String[] choice = view.inputChoice("Enter your loanID to return book: ");
        if(choice[0].equals("none")) return;
        for (String s : choice) {
            int loanID;
            try {
                loanID = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Choice " + s + " must be numeric!");
                continue;
            }
            //Check có trong fine của mình ko
            boolean inList = false;
            for (int j = 0; j < loanList.size(); j++) {
                if (loanID == loanList.get(j).getId()) {
                    inList = true;
                    services.disableLoan(id, loanID);
                    BookTrigger.payLoanTrigger(loanList.get(j).getBookID());
                    System.out.println("LoanID: " + loanID + " has been returned!");
                    loanList.remove(j);
                    break;
                }
            }
            if (!inList) System.out.println("LoanID: " + loanID + " is not in your loan.");
        }
        System.out.println();
    }


    public void chooseBookAndBorrow(int customerID) throws SQLException {
        String []choice = view.inputChoice("Enter bookID you want to borrow or none: ");
            if (!choice[0].equals( "none")){
                //Check xem con book ko
                List<Integer> choiceInt = new ArrayList<>();
                float totalMoney = 0;
                for (String value : choice) {
                    int bookID;
                    try{
                        bookID = Integer.parseInt(value);
                    }
                    catch(Exception e){
                        System.out.println(value + " must be numeric");
                        continue;
                    }

                    //Khi library het books
                    if(!services.subtractCopiesOwned(bookID)){
                        if(view.showNotEnoughCopies(CustomerServices.getBookName(bookID))){
                            services.createReservation(customerID,bookID);
                        }
                        continue;
                    }
                    if (services.checkBook(bookID)) {
                        totalMoney += CustomerServices.getBorrowedFee(bookID);
                    }
                    choiceInt.add(bookID);
                }


                if (totalMoney ==0) {
                    System.out.println("You didn't borrow anything!");
                    return;
                }
                //Show return day va total money
                if(!view.showResultBorrowBook(totalMoney)) return;

                // Khi customer ko con tien
                if(!services.subtractBalance(customerID,totalMoney)){
                    System.out.println("Not enough money!");
                    return;
                }

                for (int bookID : choiceInt) {
                    services.createLoan(customerID, bookID);
                }
                System.out.println("Successfully borrowed!");
            }
    }



    public void borrowBook(int id) throws SQLException {
        if(Fine.checkFine(id)){
            System.out.println("You have a fine. Please pay it first.");
            return;
        }

        List<String> categories = new ArrayList<>();
        Category.getCategories(categories);
        view.showCategories(categories);

        String []choice = view.inputChoice("Enter categories you want to see or none:");
        if(OperationHelper.isArrayOfInteger(choice)){
            for(int i=0;i<choice.length;i++){
                System.out.println(i+". choice: "+ choice[i]);
            }

            String sql = services.turnChoicesToSQL(choice,categories);
            if(sql == null) return;
            view.showBookByCategories(sql);
            chooseBookAndBorrow(id);
        }
        else if(choice[0].equals("none")){
            AdminView adminView = new AdminView();
            adminView.showInfoOfAllBooks();
            chooseBookAndBorrow(id);
        }

    }


    public void updateInformation(int id) throws SQLException {
        view.showAllInfo(id);
        boolean check = true;
        while (check)
        {
            switch (view.showUpdateInfoMenu()){
                case 1 -> {
                    services.updateUsername(id,view.inputUsername());
                    System.out.println();
                }
                case 2 -> {
                    services.updatePassword(id,OperationHelper.inputString("Enter new password"));
                    System.out.println();
                }
                case 3->
                {
                    services.updatePhoneNumber(id,view.inputPhoneNumber());
                    System.out.println();
                }
                case 4-> {
                    services.addBalance(id, OperationHelper.inputFloat("Input your balance: "));
                    System.out.println("Add successful.");
                    System.out.println();
                }
                default -> {
                    check = false;
                }
            }
        }

    }

    public void showReservation(int id) throws SQLException {
        view.showReservation(id);
    }

    public void returnBookTrigger(int id) throws SQLException {
        String sql = "select book.copiesOwned from book, loan where loan.id = " + id + " and book.id = loan.bookid";
        ResultSet resultSet = Mysql.statement.executeQuery(sql);
        int copies = 0;

        while (resultSet.next()){
            copies = resultSet.getInt("copiesOwned");
        }

        copies++;
        sql = "Update book set copiesOwned = " + copies+ " where id = "+id;
        Mysql.statement.executeUpdate(sql);
    }
}

package controllers;

import config.Mysql;
import models.Customer;
import services.CustomerServices;
import utils.OperationHelper;
import views.AdminView;
import views.CustomerView;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        view.showOwnFine(id);
        String[] choice = view.inputChoice("Enter your loanID to pay: ");
        if(OperationHelper.isArrayOfInteger(choice)){
                for(int i =0;i<choice.length;i++){
                    int loanID = Integer.parseInt(choice[0]);
                    if(CustomerServices.checkFine(loanID)) {
                        if(!services.subtractBalance(id,services.getFineAmount(id,loanID))){
                            System.out.println("Your account does not have enough money.");
                            return;
                        }
                        if(services.deleteFine(id,loanID)){
                            System.out.println("Fine with loanID("+ loanID +") has been paid!");
                        }
                        else System.out.println("Fine with loanID("+ loanID +") does not exist!");

                    }
                }
            }

    }

    public void payLoan(int id) throws SQLException {
        view.showBorrowedBooks(id);
        String[] choice = view.inputChoice("Enter your loanID to return book: ");
        if(OperationHelper.isArrayOfInteger(choice)){
                for(int i =0;i<choice.length;i++){
                    int loanID = Integer.parseInt(choice[0]);
                    if(services.checkLoan(loanID)) {
                        services.disableLoan(id,loanID);
                        returnBookTrigger(loanID);
                        System.out.println("LoanID: "+ loanID +" has been paid!");
                    }
                }

        }
    }


    public void chooseBookAndBorrow(int customerID) throws SQLException {
        String []choice = view.inputChoice("Enter bookID you want to borrow or none: ");
        if(OperationHelper.isArrayOfInteger(choice)){
            if (!choice[0].equals( "none")){

                //Check xem con book ko
                float totalMoney = 0;
                for (String value : choice) {
                    int bookID = Integer.parseInt(value);
                    if (services.checkBook(bookID)) {
                        totalMoney += CustomerServices.getBorrowedFee(bookID);
                    } else return;
                }

                //Show return day va total money
                view.showResultBorrowBook(totalMoney);

                // Khi customer ko con tien
                if(!services.subtractBalance(customerID,totalMoney)){
                    System.out.println("Not enough money!");
                    return;
                }

                for (String s : choice) {
                    int bookID= Integer.parseInt(s);
                    //Khi library het book
                    if(!services.subtractCopiesOwned(bookID)){
                        if(view.showNotEnoughCopies(CustomerServices.getBookName(bookID))){
                            services.createReservation(customerID,bookID);
                        }
                        else return;
                    }
                    else services.createLoan(customerID, bookID);
                }
                System.out.println("Successfully borrowed!");
            }
        }
    }



    public void borrowBook(int id) throws SQLException {
        List<String> categories = new ArrayList<>();
        CustomerServices.getCategories(categories);
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
                }
                case 2 -> {
                    services.updatePassword(id,OperationHelper.inputString("Enter new password"));
                }
                case 3->
                {
                    services.updatePhoneNumber(id,view.inputPhoneNumber());
                }
                case 4-> {
                    //view.showBalace(id)
                    services.addBalance(id,OperationHelper.inputFloat("Enter balance: "));
                    System.out.println("Add successful.");

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

package controllers;

import config.Mysql;
import jdk.jfr.Category;
import models.Customer;
import services.CustomerServices;
import utils.OperationHelper;
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
        int choice =1;
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

    public void showBorrowedBooks(int id) throws SQLException {
       view.showBorrowedBooks(id);
    }
    public void payFineOwnFine(int id) throws SQLException {
        view.showOwnFine(id);
        String choice[] = view.inputChoice("Enter your choice to pay: ");
        if(OperationHelper.isArrayOfInteger(choice)){
            if(!choice[0].equals("none")){
                for(int i =0;i<choice.length;i++){

                }
            }

            return;
        }
        System.out.println("Wrong input");
    }

    public void chooseBookAndBorrow(int custmerID) throws SQLException {
        String []choice = view.inputChoice("Enter categories you want to see or none: ");
        if(OperationHelper.isArrayOfInteger(choice)){
            if (!choice[0].equals( "none")){

                //Check xem con book ko
                float totalMoney = 0;
                for (String value : choice) {
                    int bookID = Integer.parseInt(value);
                    if (services.checkBook(bookID)) {
                        totalMoney += services.getBorrowedFee(bookID);
                    } else return;
                }

                //Show return day va total money
                view.showResultBorrowBook(totalMoney);

                // Khi customer ko con tien
                if(!services.subtractBalance(custmerID,totalMoney)){
                    System.out.println("Not enough money!");
                }

                for (String s : choice) {
                    int bookID= Integer.parseInt(s);
                    //Khi library het book
                    if(!services.subtractCopiesOwned(bookID)){
                        if(view.showNotEnoughCopies(services.getBookName(bookID))){
                            services.createReservation(custmerID,bookID);
                        }
                        else return;
                    }
                    else services.createLoan(custmerID, bookID);
                }
                System.out.println("Successfully borrowed!");
            }
        }
    }



    public void borrowBook(int id) throws SQLException {
        List<String> categories = new ArrayList<>();
        services.getCategories(categories);
        view.showCategories(categories);

        String []choice = view.inputChoice("Enter bookID you want to borrow or none:");
        if(OperationHelper.isArrayOfInteger(choice)){
            for(int i=0;i<choice.length;i++){
                System.out.println(i+". choice: "+ choice[i]);
            }

            String sql = services.turnChoicesToSQL(choice,categories);
            if(sql == null) return;
            view.showBookByCategories(sql);
            chooseBookAndBorrow(id);
        }

    }
    public void updateInformation(int id) throws SQLException {
        while (true)
        {
            switch (view.showUpdateInfoMenu()){
                case 1 -> {
                    services.updateUsername(id,view.inputPhoneNumber());
                }
                case 2 -> {
                    services.updatePassword(id,OperationHelper.inputString("Enter new password"));
                }
                case 3 ->
                {

                }
                case 4->
                {
                    services.updatePhoneNumber(id,view.inputPhoneNumber());
                }
                case 5-> {
                    services.addBalance(id,OperationHelper.inputFloat("Enter balance: "));
                }
                case 6 ->{
                    return;
                }
            }
        }

    }




}

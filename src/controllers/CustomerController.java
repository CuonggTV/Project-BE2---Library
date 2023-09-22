package controllers;

import models.Customer;
import services.CustomerServices;
import utils.OperationHelper;
import views.CustomerView;

import java.sql.SQLException;

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
    public void showOwnFine(int id) throws SQLException {
        view.showOwnFine(id);
    }

}

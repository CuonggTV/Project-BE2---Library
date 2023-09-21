package controllers;

import models.Customer;
import services.CustomerServices;
import views.CustomerView;

import java.sql.SQLException;

public class CustomerController {
    public CustomerView view;
    public CustomerServices services;

    public CustomerController() {
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
    public void showAllStudent() throws SQLException {
        view.showAllInfo();
    }
    public void login() throws SQLException {
        Customer customer = view.inputInfo();

    }

}

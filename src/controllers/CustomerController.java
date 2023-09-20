package controllers;

import models.Customer;
import services.CustomerServices;
import views.CustomerView;

import java.sql.SQLException;

public class CustomerController {
    public CustomerView customerView;
    public CustomerServices customerServices;

    public CustomerController() {
    }
    public CustomerController(CustomerServices customerServices) {
        this.customerServices = customerServices;
    }
    public CustomerController(CustomerView customerView) {
        this.customerView = customerView;
    }
    public CustomerController(CustomerView customerView, CustomerServices customerServices) {
        this.customerView = customerView;
        this.customerServices = customerServices;
    }
    public void showAllStudent() throws SQLException {
        this.customerView.showAllInfo();
    }
    public void login(){
        //this.
    }

}

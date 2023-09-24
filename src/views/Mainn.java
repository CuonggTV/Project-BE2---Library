package views;

import config.Mysql;

import java.sql.SQLException;

public class Mainn {
    public static void main(String[] args) throws SQLException {

        Mysql.getConnected();
        AdminView bookView = new AdminView();
//        bookView.showBorrowedBook();
        CustomerView customerView = new CustomerView();
        bookView.showAllCustomerAndTheirStatus();
    }
}

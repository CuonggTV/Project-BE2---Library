package views;

import controllers.CustomerController;

import java.sql.SQLException;

public class CustomerMenu extends AbstractMenu {
    private int id;
    private final CustomerController customerController;
    public final static String[] CHOICE_OPTIONS = {
            "Update information.",
            "Show borrowed books and return them.",
            "Show own fines and pay them.",
            "Show reservation.",
            "Borrow books."
    };


    public CustomerMenu(String[] choiceOptions) {
        super(choiceOptions);
        customerController = new CustomerController();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void eventHandler(int whichChoice) throws SQLException {
        switch (whichChoice){
            case 1:
                customerController.updateInformation(id);
                System.out.println();
                break;
            case 2:
                customerController.payLoan(id);
                System.out.println();
                break;
            case 3:
                customerController.payFine(id);
                System.out.println();
                break;
            case 4:
                customerController.showReservation(id);
                System.out.println();
                break;
            case 5:
                customerController.borrowBook(id);
                System.out.println();
                break;
        }
    }
}

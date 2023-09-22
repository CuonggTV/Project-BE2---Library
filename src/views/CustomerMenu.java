package views;

import controllers.CustomerController;

import java.sql.SQLException;

public class CustomerMenu extends AbstractMenu {
    private int id;
    private final CustomerController customerController;
    public final static String[] CHOICE_OPTIONS = {
            "Check borrowed books.",
            "Check own fines.",
            "Show list of books.",
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
                customerController.showBorrowedBooks(id);
                break;
            case 2:
                customerController.showOwnFine(id);
                break;
            case 3:

                break;
            case 4:

                break;
        }
    }
}

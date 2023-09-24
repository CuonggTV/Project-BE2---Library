package views;

import controllers.CustomerController;

public class AdminMenu extends AbstractMenu {
    private final CustomerController customerController;
    public final static String[] CHOICE_OPTIONS = {
            "Add books.",
            "Delete books",
            "Show all books.",
            "Show all customers and their status(borrowed or not)."
    };

    public AdminMenu(String[] choiceOptions) {
        super(choiceOptions);
        customerController = new CustomerController();
    }
        @Override
        public void eventHandler(int whichChoice) {
            switch (whichChoice){
                case 1:
                    AdminView admin = new AdminView();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
}


package views;

import controllers.CustomerController;

import java.sql.SQLException;

public class AdminMenu extends AbstractMenu {
    private final AdminView adminView;
    public final static String[] CHOICE_OPTIONS = {
            "Add books.",
            "Delete books",
            "Show all books.",
            "Show all customers and their status(borrowed or not).",
            "Show customers with fines",
            "Show customers with reservation",
            "Show customers with amount of book",
            "Add new category",
            "Update category name",
            "Add categories to book"
    };

    public AdminMenu(String[] choiceOptions) {
        super(choiceOptions);
        adminView = new AdminView();
    }
        @Override
        public void eventHandler(int whichChoice) throws SQLException {
            switch (whichChoice){
                case 1:
                    adminView.addBook();
                    break;
                case 2:
                    adminView.deleteBook();
                    break;
                case 3:
                    adminView.showInfoOfAllBooks();
                    break;
                case 4:
                    adminView.showAllCustomerAndTheirStatus();
                    break;
                case 5:
                    adminView.showAllCustomerWithFine();
                    break;
                case 6:
                    adminView.showAllCustomerWithReservation();
                    break;
                case 7:
                    adminView.addBookAmount();
                    break;
                case 8:
                    adminView.addCategory();
                    break;
                case 9:
                    adminView.updateCategoryName();
                    break;
                case 10:
                    adminView.addBookCategories();
                    break;
            }
        }
}


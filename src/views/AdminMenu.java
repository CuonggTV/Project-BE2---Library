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
                    System.out.println();
                    break;
                case 2:
                    adminView.deleteBook();
                    System.out.println();
                    break;
                case 3:
                    adminView.showInfoOfAllBooks();
                    System.out.println();
                    break;
                case 4:
                    adminView.showAllCustomerAndTheirStatus();
                    System.out.println();
                    break;
                case 5:
                    adminView.showAllCustomerWithFine();
                    System.out.println();
                    break;
                case 6:
                    adminView.showAllCustomerWithReservation();
                    System.out.println();
                    break;
                case 7:
                    adminView.addBookAmount();
                    System.out.println();
                    break;
                case 8:
                    adminView.addCategory();
                    System.out.println();
                    break;
                case 9:
                    adminView.updateCategoryName();
                    System.out.println();
                    break;
                case 10:
                    adminView.addBookCategories();
                    System.out.println();
                    break;
            }
        }
}


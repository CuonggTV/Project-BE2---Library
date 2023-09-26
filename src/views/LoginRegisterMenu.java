package views;

import controllers.CustomerController;

import java.sql.SQLException;

public class LoginRegisterMenu extends AbstractMenu{
    public final static String[] CHOICE_OPTIONS = {
            "Login",
            "Register"
    };

    private final CustomerController customerController;
    private final CustomerMenu customerMenu;
    public LoginRegisterMenu (String[] choiceOptions) {
        super(choiceOptions);
        customerController = new CustomerController();
        customerMenu = new  CustomerMenu(CustomerMenu.CHOICE_OPTIONS);
    }
    @Override
    public void eventHandler(int whichChoice) throws SQLException {
        switch (whichChoice) {
            case 1 -> {
                int id = customerController.login();
                if (id!=0) {
                    customerMenu.setId(id);
                    customerMenu.trigger();
                }
                System.out.println();
            }
            case 2 -> {
                customerController.register();
                customerMenu.trigger();
                System.out.println();
            }
        }
    }
}

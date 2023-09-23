import config.Mysql;
import utils.OperationHelper;
import views.AbstractMenu;
import views.AdminMenu;
import views.CustomerMenu;
import views.LoginRegisterMenu;

import java.io.IOException;
import java.sql.SQLException;

public class App extends AbstractMenu {
    private final static String[] CHOICE_OPTIONS = {
            "Login as admin.",
            "Login as customer."
    };
    private final LoginRegisterMenu loginRegisterMenu;
    private final AdminMenu adminMenu;

    public App(String[] choiceOptions) {
        super(choiceOptions);
        loginRegisterMenu = new LoginRegisterMenu(LoginRegisterMenu.CHOICE_OPTIONS);
        adminMenu = new AdminMenu(AdminMenu.CHOICE_OPTIONS);
    }

    public static void main(String[] args) throws SQLException {
        Mysql.getConnected();
        App app = new App(CHOICE_OPTIONS);
        app.trigger();
    }
    @Override
    public void eventHandler(int whichChoice) throws SQLException {
        switch (whichChoice) {
            case 1 -> {
                if (OperationHelper.inputString("Enter admin password: ").equals("12345")) {
                    System.out.println("Login as admin successful!");
                    adminMenu.trigger();
                } else System.out.println("Try again");
            }
            case 2 -> loginRegisterMenu.trigger();
        }
    }
}

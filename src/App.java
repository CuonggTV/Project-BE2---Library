import utils.OperationHelper;
import views.AbstractMenu;
import views.AdminMenu;
import views.CustomerMenu;

import java.io.IOException;

public class App extends AbstractMenu {
    private final static String[] CHOICE_OPTIONS = {
            "Login as admin.",
            "Login as customer"
    };
    private final CustomerMenu customerMenu;
    private final AdminMenu adminMenu;

    public App(String[] choiceOptions) {
        super(choiceOptions);
        customerMenu = new CustomerMenu(CustomerMenu.CHOICE_OPTIONS);
        adminMenu = new AdminMenu(AdminMenu.CHOICE_OPTIONS);
    }

    public static void main(String[] args) throws IOException {
        App app = new App(CHOICE_OPTIONS);
        app.trigger();
    }
    @Override
    public void eventHandler(int whichChoice) {
        switch (whichChoice){
            case 1:
                if(OperationHelper.inputString("Enter admin password: ").equals("12345")){
                    System.out.println("Login as admin successful!");
                    adminMenu.trigger();
                }
                else System.out.println("Try again");
                break;
            case 2:
                customerMenu.trigger();
                break;
        }
    }
}

package views;

public class AdminMenu extends AbstractMenu {
    public final static String[] CHOICE_OPTIONS = {
            "Add books.",
            "Delete books",
            "Show all books.",
            "Show all customers and their status(borrowed or not)."
    };

    public AdminMenu(String[] choiceOptions) {
        super(choiceOptions);
        //productController = new ProductController();
    }
        @Override
        public void eventHandler(int whichChoice) {
            switch (whichChoice){
                case 1:
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


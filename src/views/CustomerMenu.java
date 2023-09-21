package views;

public class CustomerMenu extends AbstractMenu {
    public final static String[] CHOICE_OPTIONS = {
            "Check borrowed books.",
            "Check own fines.",
            "Show list of books.",
            "Borrow books.",
            "Donate books."
    };


    public CustomerMenu(String[] choiceOptions) {
        super(choiceOptions);
        //productController = new ProductController();
    }
    @Override
    public void eventHandler(int whichChoice) {
        switch (whichChoice){
            case 1:
//                BookView bookView = new BookView();
//                bookView.showBorrowedBook();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }
}

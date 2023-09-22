package views;

import utils.OperationHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractMenu {
    private List<String> choiceOptions;

    public AbstractMenu(String [] choiceOptions) {
        this.choiceOptions = new ArrayList<>();
        this.choiceOptions.addAll(Arrays.asList(choiceOptions));
    }

    public void addChoice(String choice){
        this.choiceOptions.add(choice);
    }

    public void showMenu(){
        int i = 0;
        for(String choice : choiceOptions){
            System.out.println(++i + ". "+choice);
        }
        System.out.println("... Other number to exit.");
    }
    public int getChoice(){
        showMenu();
        return OperationHelper.inputInteger("Choose action to execute: ");
    }

    public abstract void eventHandler(int whichChoice) throws SQLException;
    public void trigger() throws SQLException {
        while(true){
            int whichChoice = this.getChoice();
            if(whichChoice>this.choiceOptions.size()){
                break;
            }
            eventHandler(whichChoice);
        }
    }

}

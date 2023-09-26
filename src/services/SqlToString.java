package services;

import utils.OperationHelper;

import java.util.List;

public class SqlToString {
    public static String SQLAddNewCategory_to_Book(String choice[], List<String> categories, int bookID){
        if(choice[0].equals("none")) return null;
        StringBuilder sqlString = new StringBuilder("UPDATE bookcategories set ");
        for(int i =0;i<choice.length;i++){

            try {
                System.out.println("Category: "+ categories.get(Integer.parseInt(choice[i])));
                int status = OperationHelper.inputIntegerWithRange("0.Disable   1.Able",0,1);
                sqlString.append("`").append(categories.get(Integer.parseInt(choice[i]))).append("` = ").append(status);
            }
            catch (Exception e){
                System.out.println("Choice must be numeric!");
                return null;
            }
            if (i!= choice.length-1){
                sqlString.append(", ");
            }
        }
        sqlString.append(" where bookID = ").append(bookID);
        return sqlString.toString();
    }

}

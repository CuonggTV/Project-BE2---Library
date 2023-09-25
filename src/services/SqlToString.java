package services;

import utils.OperationHelper;

import java.util.List;

public class SqlToString {
    public static String SQLAddNewCategory_to_Book(String choice[], List<String> categories, int bookID){
        StringBuilder sqlString = new StringBuilder("UPDATE bookcategories set ");
        for(int i =0;i<choice.length;i++){

            int status = OperationHelper.inputIntegerWithRange("1.Able  2.Disable",1,2);
            sqlString.append("`").append(categories.get(Integer.parseInt(choice[i]))).append("` = ").append(status);


//            try {
//                sqlString.append("`").append(categories.get(Integer.parseInt(choice[i]))).append("` = 1 ");
//            }
//            catch (Exception e){
//                System.out.println("This is not in categories");
//                return null;
//            }
            if (i!= choice.length-1){
                sqlString.append(", ");
            }
        }
        sqlString.append(" where bookID = ").append(bookID);
        return sqlString.toString();
    }

}

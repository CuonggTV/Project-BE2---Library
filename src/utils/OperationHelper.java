package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class OperationHelper {

    public static String inputString(String message)
    {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(message);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Value can not be empty!");
        } while (true);
    }

    public static String inputOnlyAlphabet(String message) {
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println(message);
        while (true) {
            System.out.print("Enter alphabetic characters only (maximum 100 characters): ");
            input = scanner.nextLine();

            if (input.matches("[a-zA-Z]+") && input.length() <= 100) {
                break;
            } else {
                System.out.println("Invalid input. Please enter alphabetic characters only (maximum 100 characters).");
            }
        }

        return input;
    }

    public static int inputInteger(String message) {
        int value;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println(message);
            try{
                value = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Value must be a number");
                continue;
            }
            break;
        }while (true);


        return value;
    }

    public static float inputNotNegativeNumber(String message) {
        Scanner sc = new Scanner(System.in);
        System.out.println("You can add balance later by typing 0 but do not input negative number! ");
        System.out.println(message);
        float balance;
        do {
            balance = sc.nextFloat();
            if(balance < 0)
            {
                System.out.println("Your balance cannot negative!\nPlease input again: ");
            }
        } while (balance < 0);
        return balance;
    }

    public static Integer inputIntegerWithRange(String message, int min, int max) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(message);
            int value;
            try{
                value = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Value must be a number");
                continue;
            }
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Value must be in range of " + min + " and " + max);
        } while (true);
    }

    public static boolean isNumeric(String num){
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException e) {
            System.out.println("Must be a number!");
            return false;
        }
        return true;
    }


    public static float inputFloat(String message)
    {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);

        return sc.nextFloat();
    }

    public static boolean confirm(String confirmMess){
        System.out.println(confirmMess);
        if(OperationHelper.inputIntegerWithRange("1.Yes     2.No",1,2) ==1)
            return true;
        return false;
    }
    public static boolean isArrayOfInteger(String []arr){
        for (String s : arr) {
            try {
                Integer.parseInt(s);
            } catch (NumberFormatException e) {
                if(!arr[0].equals("none")){
                    System.out.println("Wrong input.");
                }

                return false;
            }
        }
        return true;
    }
    
    public static String DateString(int plusMonth){
        LocalDateTime ldt = LocalDateTime.now().plusMonths(plusMonth);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        return format1.format(ldt);
    }

}

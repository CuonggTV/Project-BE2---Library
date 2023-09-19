package utils;

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

    public static int inputInteger(String message)
    {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);

        return sc.nextInt();
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


    public static float inputFloat(String message)
    {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);

        return sc.nextFloat();
    }
}

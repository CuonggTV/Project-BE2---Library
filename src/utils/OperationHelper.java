package utils;

import java.util.Scanner;

public class OperationHelper {

    public static String inputFromKeyBoard(String message)
    {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);

        return sc.nextLine();
    }

    public static int inputInteger(String message)
    {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);

        return sc.nextInt();
    }

    public static float inputFloat(String message)
    {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);

        return sc.nextFloat();
    }
}

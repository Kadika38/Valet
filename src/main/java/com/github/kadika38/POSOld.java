package com.github.kadika38;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class POSOld {
    
    //MUST SPECIFY AWS PATH IN ARGS[0] !!!
    public static void use(String arg) {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        APICallMakerOld apiCaller = new APICallMakerOld(arg);

        System.out.println("Welcome to the automated Valet system!");

        while (!exit) {
            apiCaller.requestTest();


            /* System.out.println("Exit? (Y/N)");
            String exitLine = scanner.nextLine();
            if (exitLine.equals("Y")) {
                exit = true;
            } */
            exit = true;
        }

        scanner.close();
    }
}
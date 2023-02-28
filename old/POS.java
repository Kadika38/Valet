import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class POS {
    
    //MUST SPECIFY AWS PATH IN ARGS[0] !!!
    public static void main(String[] args) {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        APICallMakerOld apiCaller = new APICallMakerOld(args[0]);

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
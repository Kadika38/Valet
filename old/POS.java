import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class POS {
    //currently this system takes user input via a Scanner
    //later on it may be updated to have a gui, but that is not the main concern as of right now

    public static Vehicle manuallyCreateVehicle() {
        //vcs = Vehicle Creation Scanner
        Scanner vcs = new Scanner(System.in);
        Map<String, String> map = new HashMap<>();

        System.out.println("Creating new vehicle.  To skip a step, type 'skip' into the entry");
        System.out.println("Enter vehicle license plate:");
        String lp = vcs.nextLine();
        if (!lp.equals("skip")) {
            map.put("licensePlate", lp);
        }
        System.out.println("Enter license plate state or country");
        String s = vcs.nextLine();
        if (!s.equals("skip")) {
            map.put("licensePlateOrigin", s);
        }
        System.out.println("Enter vehicle make:");
        String mk = vcs.nextLine();
        if (!mk.equals("skip")) {
            map.put("make", mk);
        }
        System.out.println("Enter vehicle color:");
        String c = vcs.nextLine();
        if (!c.equals("skip")) {
            map.put("color", c);
        }
        System.out.println("Enter guest's last name:");
        String ln = vcs.nextLine();
        if (!ln.equals("skip")) {
            map.put("guestLastName", ln);
        }
        System.out.println("Enter guest's first name:");
        String fn = vcs.nextLine();
        if (!fn.equals("skip")) {
            map.put("guestFirstName", fn);
        }
        System.out.println("Create vehicle? (Y/N)");
        boolean finalize = vcs.nextLine().equals("Y") ? true : false;
        vcs.close();
        if (finalize) {
            return new Vehicle(map);
        } else {
            return null;
        }
    }
    
    //MUST SPECIFY AWS PATH IN ARGS[0] !!!
    public static void main(String[] args) {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        APICallMaker apiCaller = new APICallMaker(args[0]);

        System.out.println("Welcome to the automated Valet system!");

        while (!exit) {
            System.out.println("\n");
            System.out.println("1) New Vehicle Parked");
            System.out.println("2) Edit Existing Vehicle Info");
            System.out.println("3) Vehicle Exiting");
            //more options to come

            //take chosen option input
            String chosenOption = scanner.nextLine();
            switch (chosenOption) {
                case "1":
                    //enter new vehicle info
                    Vehicle v = manuallyCreateVehicle();
                    v.sendToDB(apiCaller);
                    break;
                
                case "2":
                    //edit existing vehicle info
                    break;
                
                case "3":
                    //exiting vehicle process
                    break;
                
                case "4":
                    apiCaller.vehicleUpdateTest();
                
                default:
                    System.out.println("Unknown input value");

            }


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
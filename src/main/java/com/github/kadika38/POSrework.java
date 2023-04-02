package com.github.kadika38;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class POSrework {
    Vehicle vehicle;
    Scanner scanner;
    APICallMaker api;
    
    POSrework(String url) {
        this.vehicle = null;
        this.scanner = new Scanner(System.in);
        this.api = new APICallMaker(url);

        // create the menu using my MenuObject menu system
        // this requires creating each menu's options before the menu itself
        // start from the bottom @ "Opening menu" and follow upwards


        // Vehicle Operations Opening Action
        Action vehicleOpsOpen = new Action(() -> performOnVehicleOpsOpen(vehicleOpsMenu));

        // Vehicle Operations Menu
        LinkedHashMap<String, MenuObject> vehicleOpsMenuMap = new LinkedHashMap<String, MenuObject>();
        // CONTINUE FROM HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


        //Employee Operations Menu

        // Opening menu
        LinkedHashMap<String, MenuObject> menu1map = new LinkedHashMap<String, MenuObject>();
        menu1map.put("Vehicle Operations", vehicleOpsOpen);
        menu1map.put("Employee Operations", employeeOpsOpen);
        Menu menu1 = new Menu("Welcome to the Valet System.  Input 'E' to exit any menu.", menu1map);
        
    }

    // Function to perform in Action vehicleOpsOpen
    // takes VID input, validates it, then retrieves vehicle from db, then opens next menu
    private void performOnVehicleOpsOpen(Menu nextMenu) {
        System.out.println("Enter Vehicle ID:");
        String enteredVid = scanner.nextLine();
        while (!Vehicle.isValidVehicleID(enteredVid)) {
            if ("E".equals(enteredVid)) {
                System.out.println("Exiting.");
                return;
            }
            System.out.println("Invalid Vehicle ID, please enter a valid Vehicle ID.");
            enteredVid = scanner.nextLine();
        }

        this.vehicle = api.retrieveVehicleFromDB(enteredVid);
        nextMenu.open();
    }
}

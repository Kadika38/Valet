package com.github.kadika38;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class POSrework {
    Vehicle vehicle;
    Scanner scanner;
    APICallMaker api;
    Employee user;


    // create the menu using my MenuObject menu system
    // this requires creating each menu's options before the menu itself
    // start from the bottom @ "Menu1" and follow upwards

    // Menu1 / Vehicle Operations / Update Vehicle Info -> update vehicle options actions
    Action vuLPAction = new Action(() -> vuLPUpdate());
    Action vuLPSAction = new Action(() -> vuLPSUpdate());
    Action vuMakeAction = new Action(() -> vuMakeUpdate());
    Action vuColorAction = new Action(() -> vuColorUpdate());
    Action vuLocationAction = new Action(() -> vuLocationUpdate());
    Action vuGFNAction = new Action(() -> vuGFNUpdate());
    Action vuGLNAction = new Action(() -> vuGLNUpdate());
    Action vuRoomNumAction = new Action(() -> vuRoomNumUpdate());
    Action vuFinishAction = new Action(() -> vuFinish());

    // Menu1 / Vehicle Operations / Update Vehicle Info (Menu)
    LinkedHashMap<String, MenuObject> vuOptions = new LinkedHashMap<String, MenuObject>();
    vuOptions.put("License Plate", vuLPAction);
    vuOptions.put("License Plate State", vuLPSAction);
    vuOptions.put("Make", vuMakeAction);
    vuOptions.put("Color", vuColorAction);
    vuOptions.put("Location", vuLocationAction);
    vuOptions.put("Guest First Name", vuGFNAction);
    vuOptions.put("Guest Last Name", vuGLNAction);
    vuOptions.put("Room Number", vuRoomNumAction);
    vuOptions.put("Apply Changes", vuApplyChangesAction);
    Menu vuMenu = new Menu("Update what vehicle info?", vuOptions);

    // Menu1 / Vehicle Operations / Vehicle Exit / Close / Normal Price (Action)
    Action closeNormalPrice = new Action(() -> performOnCloseNormalPrice());

    // Menu1 / Vehicle Operations / Vehicle Exit / Close / Custom Price (Action)
    Action closeCustomPrice = new Action(() -> performOnCloseCustomPrice());

    // Menu1 / Vehicle Operations / Vehicle Exit / Close / Comp (Action)
    Action closeComp = new Action(() -> performOnCloseComp());

    // Menu1 / Vehicle Operations / Vehicle Exit / Close (Menu)
    LinkedHashMap<String, MenuObject> vehicleExitCloseMenuMap = new LinkedHashMap<String, MenuObject>();
    vehicleExitCloseMenuMap.put("Normal Price", closeNormalPrice);
    vehicleExitCloseMenuMap.put("Custom Price", closeCustomPrice);
    vehicleExitCloseMenuMap.put("Comp", closeComp);
    Menu vehicleExitCloseMenu = new Menu("Choose how to close this vehicle:", vehicleExitCloseMenuMap);

    // Menu1 / Vehicle Operations / Vehicle Exit / Will Return (Action)
    Action vehicleExitWillReturn = new Action(() -> performOnVehicleExitWillReturn());

    // Menu1 / Vehicle Operations / Vehicle Exit (Menu)
    LinkedHashMap<String, MenuObject> vehicleExitMenuMap = new LinkedHashMap<String, MenuObject>();
    vehicleExitMenuMap.put("Close", vehicleExitCloseMenu);
    vehicleExitMenuMap.put("Will Return", vehicleExitWillReturn);
    Menu vehicleExitMenu = new Menu("Vehicle Exit", vehicleExitMenuMap);

    // Menu1 / Vehicle Operations / Prepay (Action)
    Action prepayOpen = new Action(() -> performOnPrepayOpen());

    // Menu1 / Vehicle Operations / View Vehicle Logs (Action)
    Action viewVehicleLogsOpen = new Action(() -> performOnViewVehicleLogsOpen());

    // Menu1 / Vehicle Operations (Menu)
    LinkedHashMap<String, MenuObject> vehicleOpsMenuMap = new LinkedHashMap<String, MenuObject>();
    vehicleOpsMenuMap.put("Update Vehicle Information", vuMenu);
    vehicleOpsMenuMap.put("Vehicle Exit", vehicleExitMenu);
    vehicleOpsMenuMap.put("Prepay", prepayOpen);
    vehicleOpsMenuMap.put("View Vehicle Logs", viewVehicleLogsOpen);
    Menu vehicleOpsMenu = new Menu("Vehicle Operations:", vehicleOpsMenuMap);

    // Menu1 / Vehicle Operations (Action)
    Action vehicleOpsOpen = new Action(() -> performOnVehicleOpsOpen(vehicleOpsMenu));

    // Menu1 / Emplotee Operations / Add New Employee (Action)
    Action addNewEmployee = new Action(() -> performOnAddNewEmployee());

    // Menu / Employee Operations / Edit Existing Employee (Action)
    Action editExistingEmployee = new Action(() -> performOnEditExistingEmployee());

    // Menu1 / Employee Operations (Menu)
    LinkedHashMap<String, MenuObject> employeeOpsMenuMap = new LinkedHashMap<String, MenuObject>();
    employeeOpsMenuMap.put("Add New Employee", addNewEmployee);
    employeeOpsMenuMap.put("Edit Existing Employee", editExistingEmployee);
    Menu employeeOpsMenu = new Menu("Employee Operations:", employeeOpsMenuMap);

    // Menu1 / Employee Operations (Action)
    Action employeeOpsOpen = new Action(() -> performOnEmployeeOpsOpen(employeeOpsMenu));

    // Menu1 (Menu)
    LinkedHashMap<String, MenuObject> menu1map = new LinkedHashMap<String, MenuObject>();
    menu1map.put("Vehicle Operations", vehicleOpsOpen);
    menu1map.put("Employee Operations", employeeOpsOpen);
    Menu menu1 = new Menu("Welcome to the Valet System.  Input 'E' to exit any menu.", menu1map);

    
    POSrework(String url) {
        this.vehicle = null;
        this.scanner = new Scanner(System.in);
        this.api = new APICallMaker(url);
        this.user = null;
    }

    // Functions used in menu Actions

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

    private void vuLPUpdate() {
        System.out.println("Enter License Plate");
        String enteredLP = scanner.nextLine();
        System.out.println("New License Plate: " + enteredLP + ".  Apply change? 'Y' to confirm, any other input to cancel.");
        String lpconfirmed = scanner.nextLine();
        if ("Y".equals(lpconfirmed)) {
            this.vehicle.setLicensePlate(enteredLP);
            System.out.println("Change saved unless input did not meet License Plate requirements.");
        } else {
            System.out.println("Canceled.");
        }
        vuMenu.open();
    }

    private void vuLPSUpdate() {
        System.out.println("Enter License Plate State (2 Characters):");
        String enteredLPS = scanner.nextLine();
        System.out.println("New License Plate State: " + enteredLPS + ".  Apply change?  'Y' to confirm, any other input to cancel.");
        String lpsconfirmed = scanner.nextLine();
        if ("Y".equals(lpsconfirmed)) {
            this.vehicle.setLicensePlateState(enteredLPS);
            System.out.println("Change saved unless input did not meet License Plate State requirements.");
        } else {
            System.out.println("Canceled.");
        }
        vuMenu.open();
    }

    // verifys user credentials before opening the employee operations menu
    private void performOnEmployeeOpsOpen(Menu nextMenu) {
        boolean succesfulLogIn = false;

        while (!succesfulLogIn) {
            System.out.println("Please log in or input 'E' to exit.  Enter Employee ID:");
            String enteredEid = scanner.nextLine();
            System.out.println("Enter password:");
            String enteredPw = scanner.nextLine();

            if ("E".equals(enteredEid) | "E".equals(enteredPw)) {
                System.out.println("Exiting.");
                return;
            }

            if (Employee.isValidEmployeeID(enteredEid)) {
                if (api.employeeLogIn(enteredEid, enteredPw)) {
                    if (api.getEmployeeSystemAccess(enteredEid) > 2) {
                        succesfulLogIn = true;
                    } else {
                        System.out.println("System access level not high enough.  Exiting.");
                        return;
                    }
                } else {
                    System.out.println("Incorrect password.");
                }
            } else {
                System.out.println("Invalid Employee ID.");
            }
        }

        nextMenu.open();
    }
}

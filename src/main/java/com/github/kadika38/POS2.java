package com.github.kadika38;

import java.security.KeyRep;
import java.util.Scanner;

public class POS2 {
    Garage garage;
    Employee user;
    APICallMaker api;
    Scanner scanner;

    POS2(Garage garage, String url) {
        this.garage = garage;
        this.api = new APICallMaker(url);
        this.user = null;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        this.user = login();
        homeMenu();
    }

    // Handles user login and returns that employee
    private Employee login() {
        
    }

    // This is the first menu that opens after login
    private void homeMenu() {
        // basic menu offering vehicle operations and employee operations
        boolean keepRunning = true;
        Integer choice = -1;
        while (keepRunning) {
            System.out.println("1) Vehicle Operations\n2) Employee Operations\n3) Exit Menu");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    vehicleOpsMenu();
                    break;
                case 2:
                    employeeOpsMenu();
                    break;
                case 3:
                    keepRunning = false;
                    System.out.println("Exiting menu.");
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }

    private void vehicleOpsMenu() {
        // basic menu offering different vehicle oriented operations
        boolean keepRunning = true;
        Integer choice = -1;
        while (keepRunning) {
            System.out.println("1) Update Vehicle Information\n2) Vehicle Exit\n3) Prepay\n4) View Vehicle Logs\n5) Exit Menu");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    updateVehicleInformationMenu();
                    break;
                case 2:
                    vehicleExitMenu();
                    break;
                case 3:
                    prepayMenu();
                    break;
                case 4:
                    viewVehicleLogsMenu();
                    break;
                case 5:
                    keepRunning = false;
                    System.out.println("Exiting Menu.");
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }

    private void updateVehicleInformationMenu() {
        boolean keepRunning = true;
        Integer choice = -1;
        Vehicle v = null;
        String vName;
        while (keepRunning) {
            if (v == null) {
                vName = "NONE";
            }
            System.out.println("Current vehicle: " + vName);
            System.out.println("1) Enter Vehicle ID of Current Vehicle\n2) Update Location\n3) Update Make\n4) Update Color\n5) Update License Plate\n6) Update License Plate Origin\n7) Update Guest First Name\n8) Update Guest Last Name\n9) Update Room Number\n10) Save Changes\n11) Exit Menu");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    Vehicle probablyV = getVehicleFromVIDInput();
                    if (probablyV != null) {
                        v = probablyV;
                        vName = v.getVid();
                    }
                    break;
                case 2:
                    String probablyLocation = getVehicleLocationInput();
                    if (probablyLocation != null) {
                        v.setLocation(probablyLocation);
                    }
                    break;
                case 3:
                    String probablyMake = getVehicleMakeInput();
                    if (probablyMake != null) {
                        v.setMake(probablyMake);
                    }
                    break;
                case 4:
                    String probablyColor = getVehicleColorInput();
                    if (probablyColor != null) {
                        v.setColor(probablyColor);
                    }
                    break;
                case 5:
                    String probablyLP = getVehicleLPInput();
                    if (probablyLP != null) {
                        v.setLicensePlate(probablyLP);
                    }
                    break;
                case 6:
                    String probablyLPS = getVehicleLPSInput();
                    if (probablyLPS != null) {
                        v.setLicensePlateState(probablyLPS);
                    }
                    break;
                case 7:
                    String probablyGFN = getGFNInput();
                    if (probablyGFN != null) {
                        v.setGuestFirstName(probablyGFN);
                    }
                    break;
                case 8:
                    String probablyGLN = getGLNInput();
                    if (probablyGLN != null) {
                        v.setGuestLastName(probablyGLN);
                    }
                    break;
                case 9:
                    Integer probablyRM = getRMInput();
                    if (probablyRM != null) {
                        v.setRoomNumber(probablyRM);
                    }
                    break;
                case 10:
                    vehicleUpdateFinalize(v);
                    break;
                case 11:
                    keepRunning = false;
                    System.out.println("Exiting Menu.");
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }

    // Takes in input and validates it as a VID, then returns the associated Vehicle using the api
    private Vehicle getVehicleFromVIDInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter Vehicle ID or input 'E' to exit:");
            String enteredVid = scanner.nextLine();
            if ("E".equals(enteredVid)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else if (Vehicle.isValidVehicleID(enteredVid)) {
                // updating keepRunning isn't necessary here but it feels safer to have
                keepRunning = false;
                System.out.println("Retrieving vehicle data...");
                return this.api.retrieveVehicleFromDB(enteredVid);
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private String getVehicleLocationInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter Spot or input 'E' to exit:");
            String enteredSpot = scanner.nextLine();
            if ("E".equals(enteredSpot)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else if (this.garage.hasSpot(enteredSpot)) {
                keepRunning = false;
                return enteredSpot;
            } else if (!this.garage.hasSpot(enteredSpot)) {
                System.out.println("Invalid Spot.");
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private String getVehicleMakeInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter Make or input 'E' to exit:");
            String enteredMake = scanner.nextLine();
            if ("E".equals(enteredMake)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            // TODO: implement a vehicle make list, use it to validate the make here
            } else if (enteredMake.length() > 0) {
                keepRunning = false;
                return enteredMake;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private String getVehicleColorInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter Color or input 'E' to exit:");
            String enteredColor = scanner.nextLine();
            if ("E".equals(enteredColor)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            // TODO: implement a vehicle color list, use it to validate the color here
            } else if (enteredColor.length() > 0) {
                keepRunning = false;
                return enteredColor;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private String getVehicleLPInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter License Plate or input 'E' to exit:");
            String enteredLP = scanner.nextLine();
            if ("E".equals(enteredLP)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else if (enteredLP.length() > 1 && enteredLP.length() < 9) {
                keepRunning = false;
                return enteredLP;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private String getVehicleLPSInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter License Plate State (2 Alphabetical Characters) or input 'E' to exit:");
            String enteredLPS = scanner.nextLine();
            if ("E".equals(enteredLPS)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else if (enteredLPS.length() == 2 && Character.isLetter(enteredLPS.charAt(0)) && Character.isLetter(enteredLPS.charAt(1))) {
                keepRunning = false;
                return enteredLPS;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private String getGFNInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter Guest First Name or input 'E' to exit:");
            String enteredGFN = scanner.nextLine();
            if ("E".equals(enteredGFN)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else if (enteredGFN.length() > 0) {
                keepRunning = false;
                return enteredGFN;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private String getGLNInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter Guest Last Name or input 'E' to exit:");
            String enteredGLN = scanner.nextLine();
            if ("E".equals(enteredGLN)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else if (enteredGLN.length() > 0) {
                keepRunning = false;
                return enteredGLN;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private Integer getRMInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter Room Number or input 'E' to exit:");
            String enteredRM = scanner.nextLine();
            if ("E".equals(enteredRM)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else {
                try {
                    Integer rm = Integer.parseInt(enteredRM);
                    // TODO: implement a room list to validate room numbers, use it here
                    keepRunning = false;
                    return rm;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        }
    }

    private void vehicleUpdateFinalize(Vehicle v) {
        System.out.println("Vehicle info unsaved:");
        v.printInfo();
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Confirm and save? 'Y' to confirm, 'E' to exit:");
            String res = scanner.nextLine();
            if ("Y".equals(res)) {
                System.out.println("Saving changes...");
                this.api.sendVehicleToDB(v);
                Log log = new Log(this.user.getEid(), v.getVid(), "Vehicle information updated");
                this.api.sendLogToDB(log);
                this.garage.update(v);
            } else if ("E".equals(res)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }
}

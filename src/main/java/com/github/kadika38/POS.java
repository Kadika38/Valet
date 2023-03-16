package com.github.kadika38;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class POS {
    Map<Spot, Vehicle> garage;
    ArrayList<Spot> spotList;
    APICallMaker api;
    Employee user;
    Scanner scanner;

    POS(ArrayList<Spot> spotList, String url) {
        this.spotList = spotList;
        this.garage = new HashMap<Spot, Vehicle>();
        for (Spot spot : spotList) {
            garage.put(spot, null);
        }
        this.api = new APICallMaker(url);
        this.user = null;
        this.scanner = new Scanner(System.in);
        this.spotList = null;
    }

    public void setActiveUser(String eid, String pw) {
        if (api.employeeLogIn(eid, pw)) {
            this.user = api.retrieveEmployeeFromDB(eid);
            System.out.println("Logged in as " + this.user.getName());
        } else {
            System.out.println("Incorrect Login information.  Access denied.");
        }
    }

    public void vehicleOps(String vid) {
        if (this.user != null) {
            if (Vehicle.isValidVehicleID(vid)) {
                Vehicle currentVehicle = api.retrieveVehicleFromDB(vid);

                System.out.println("Welcome to Vehicle Operations.  To exit any menu, enter E.");

                boolean vo = true;
                int failCount = 0;
                while (vo) {
                    System.out.println("Vehicle Operations:\n1) Update Vehicle Information\n2) Vehicle Exit\n3) Prepay\n4) View Vehicle and Vehicle Logs");
                    String case1 = scanner.nextLine();
                    switch (case1) {
                        case "1":
                            // Update Vehicle Information
                            System.out.println("Update Vehicle Information");
                            boolean uvi = true;
                            int uviFailCount = 0;
                            while (uvi) {
                                System.out.println("Options:\n1) Location\n2) Make\n3) Color\n4) License Plate\n5) License Plate State\n6) Guest First Name\n7) Guest Last Name\n8) Room Number\n9) Save and Complete");
                                String uviOption = scanner.nextLine();
                                switch (uviOption) {
                                    case "1":
                                        // set location
                                        System.out.println("Enter location:");
                                        String l = scanner.nextLine();
                                        boolean isRealSpot = false;
                                        for (Spot spot : spotList) {
                                            if (spot.getName() == l) {
                                                isRealSpot = true;
                                                garage.put(spot, currentVehicle);
                                                currentVehicle.setLocation(l);
                                                System.out.println("Location updated.");
                                            }
                                        }
                                        if (!isRealSpot) {
                                            System.out.println("Invalid input." + l + " is not a valid location.");
                                        }
                                        break;
                                    case "2":
                                        // set make
                                        System.out.println("Enter make:");
                                        String m = scanner.nextLine();
                                        currentVehicle.setMake(m);
                                        System.out.println("Make updated.");
                                        break;
                                    case "3":
                                        // set color
                                        System.out.println("Enter color:");
                                        String c = scanner.nextLine();
                                        currentVehicle.setColor(c);
                                        System.out.println("Color updated.");
                                        break;
                                    case "4":
                                        // set license plate
                                        System.out.println("Enter license plate:");
                                        String lp = scanner.nextLine();
                                        if (lp.length() > 1 && lp.length() < 9) {
                                            currentVehicle.setLicensePlate(lp);
                                            System.out.println("License plate updated.");
                                        } else {
                                            System.out.println("Invalid input. " + lp + " is not a valid license plate.");
                                        }
                                        break;
                                    case "5":
                                        // set license plate state
                                        System.out.println("Enter license plate state:");
                                        String lpc = scanner.nextLine();
                                        if (lpc.length() == 2 && Character.isLetter(lpc.charAt(0)) && Character.isLetter(lpc.charAt(1))) {
                                            currentVehicle.setLicensePlateState(lpc);
                                            System.out.println("License plate state updated.");
                                        } else {
                                            System.out.println("Invalid input. " + lpc + " is not a valid license plate state.");
                                        }
                                        break;
                                    case "6":
                                        // set guest first name
                                        System.out.println("Enter guest's first name:");
                                        String gfn = scanner.nextLine();
                                        currentVehicle.setGuestFirstName(gfn);
                                        System.out.println("Name updated.");
                                        break;
                                    case "7":
                                        // set guest last name
                                        System.out.println("Enter guest's last name:");
                                        String gln = scanner.nextLine();
                                        currentVehicle.setGuestLastName(gln);
                                        System.out.println("Name updated.");
                                        break;
                                    case "8":
                                        // set room number
                                        System.out.println("Enter room number:");
                                        String rm = scanner.nextLine();
                                        try {
                                            Integer rmInt = Integer.parseInt(rm);
                                            currentVehicle.setRoomNumber(rmInt);
                                            System.out.println("Room number updated.");
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid input. " + rm + " is not a valid room number");
                                        }
                                        break;
                                    case "9":
                                        // save and complete
                                        System.out.println("Saving...");
                                        // assume that if vehicle was being parked, it is now parked
                                        if (currentVehicle.getStatus() == "Being Parked") {
                                            currentVehicle.setStatus("In");
                                        }
                                        // send updated vehicle to db
                                        api.sendVehicleToDB(currentVehicle);
                                        // create and send log
                                        Log log = new Log(user.getEid(), vid, "Vehicle updated.");
                                        api.sendLogToDB(log);
                                        System.out.println("Saved.");
                                        // close menu
                                        uvi = false;
                                        break;
                                    case "E":
                                        // exit
                                        System.out.println("Exiting.");
                                        uvi = false;
                                        break;
                                    default:
                                        System.out.println("Invalid input: " + uviOption);
                                        uviFailCount++;
                                        if (uviFailCount > 9) {
                                            uvi = false;
                                            System.out.println("Too many invalid inputs.  Closing menu.");
                                        }
                                        break;
                                }
                            }
                            break;
                        case "2":
                            // Vehicle Exit
                            break;
                        case "3":
                            // Prepay
                            break;
                        case "4":
                            // View Vehicle & Vehicle Logs
                            break;
                        case "E":
                            // Exit
                            vo = false;
                            System.out.println("Closing function.");
                            break;
                        default:
                            System.out.println("Invalid input: " + case1);
                            failCount++;
                            if (failCount > 9) {
                                vo = false;
                                System.out.println("Too many invalid inputs.  Closing function.");
                            }
                            break;
                    }
                }
            }
        }
    }
}

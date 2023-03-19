package com.github.kadika38;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class POS {
    Map<Spot, Vehicle> garage;
    ArrayList<Spot> spotList;
    APICallMaker api;
    Employee user;
    Scanner scanner;
    int dailyRate;
    int hourlyRate;

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
        this.dailyRate = 0;
        this.hourlyRate = 0;
    }

    public void setActiveUser(String eid, String pw) {
        if (api.employeeLogIn(eid, pw)) {
            this.user = api.retrieveEmployeeFromDB(eid);
            System.out.println("Logged in as " + this.user.getName());
        } else {
            System.out.println("Incorrect Login information.  Access denied.");
        }
    }

    public void setRates(int dailyRate, int hourlyRate) {
        this.dailyRate = dailyRate;
        this.hourlyRate = hourlyRate;
    }

    private Integer getPrice(Integer timeParked) {
        // calculates the price given the amount of time the vehicle was parked
        // assumes timeParked is in minutes
        int days = timeParked / (24 * 60);
        int hours = timeParked % (24 * 60);
        if (hours * this.hourlyRate < this.dailyRate) {
            return (days * this.dailyRate) + (hours * this.hourlyRate);
        } else {
            return ((days + 1) * this.dailyRate);
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
                            System.out.println("Vehicle Exiting:\n1) Close\n2) Will Return");
                            int veFailCount = 0;
                            boolean ve = true;
                            while (ve) {
                                String veOption = scanner.nextLine();
                                switch (veOption) {
                                    case "1":
                                        // close
                                        System.out.println("Pricing:\n1) Normal\n2) Custom\n3) Comp");
                                        int vecFailCount = 0;
                                        boolean vec = true;
                                        while (vec) {
                                            String vecOption = scanner.nextLine();
                                            switch (vecOption) {
                                                case "1":
                                                    // normal price
                                                    int price = getPrice(currentVehicle.getTotalNewTimeParked()) - currentVehicle.getPaidAmount();
                                                    System.out.println("Price: " + price);
                                                    boolean confirmedNormalPricePaid = yesNoConfirmation("Confirm transaction when complete (Y/N)");
                                                    boolean confirmationRetry = !confirmedNormalPricePaid;
                                                    int confirmationRetryFailCount = 0;
                                                    while (confirmationRetry) {
                                                        System.out.println("Transaction incomplete, enter E to exit, or Y to retry transaction confirmation.");
                                                        String tiRes = scanner.nextLine();
                                                        switch (tiRes) {
                                                            case "E":
                                                                confirmationRetry = true;
                                                                break;
                                                            case "Y":
                                                                confirmedNormalPricePaid = yesNoConfirmation("Confirm transaction when complete (Y/N)");
                                                                if (confirmedNormalPricePaid) {
                                                                    confirmationRetry = true;
                                                                }
                                                                break;
                                                            default:
                                                                confirmationRetryFailCount++;
                                                                if (confirmationRetryFailCount > 9) {
                                                                    confirmationRetry = true;
                                                                }                            
                                                                break;                                    
                                                        }
                                                    }
                                                    if (!confirmedNormalPricePaid) {
                                                        vec = false;
                                                        ve = false;
                                                        break;
                                                    }
                                                    Log normalPricePaidLog = new Log(user.getEid(), vid, "Vehicle Charged Normal Price: " + price + " and closed");
                                                    api.sendLogToDB(normalPricePaidLog);
                                                    // create vehicle update json
                                                    String closedPaidNormalPriceJson = "{\"id\": \"" + vid + "\", \"status\": \"closed\", \"paidAmount\": \"" + (price + currentVehicle.getPaidAmount()) + "\"}";
                                                    api.updateVehicleClosed(closedPaidNormalPriceJson);
                                                    System.out.println("Transaction confirm.  Vehicle closed.");
                                                    // check if vehicle is blocked
                                                    for (Spot spot : spotList) {
                                                        if (spot.getName() == currentVehicle.getLocation()) {
                                                            for (Spot blocker : spot.getBlockedBy()) {
                                                                System.out.println("Blocked by vehicle " + this.garage.get(blocker).getVid() + " in spot " + blocker.getName());
                                                            }
                                                            break;
                                                        }
                                                    }
                                                    vec = false;
                                                    ve = false;
                                                    break;
                                                case "2":
                                                    // custom price
                                                    System.out.println("Please provide password: ");
                                                    String cpPw = scanner.nextLine();
                                                    if (!api.employeeLogIn(this.user.getEid(), cpPw) && this.user.getSystemAccess() < 2) {
                                                        System.out.println("Access denied.");
                                                        vec = false;
                                                        break;
                                                    }
                                                    System.out.println("Access granted.");
                                                    System.out.println("Vehicle was here for " + (currentVehicle.getTotalNewTimeParked() / (24 * 60)) + " days, " + ((currentVehicle.getTotalNewTimeParked() % (24 * 60)) / 60) + " hours, and " + (currentVehicle.getTotalNewTimeParked() % 60) + " minutes.");
                                                    System.out.println("Already paid: " + currentVehicle.getPaidAmount());
                                                    System.out.println("Enter custom price: ");
                                                    Integer customPrice = -1;
                                                    int cpFailCount = 0;
                                                    boolean cp = true;
                                                    while (cp) {
                                                        String customePriceRes = scanner.nextLine();
                                                        try {
                                                            customPrice = Integer.parseInt(customePriceRes);
                                                        } catch (NumberFormatException e) {
                                                            cpFailCount++;
                                                            if (cpFailCount > 9) {
                                                                break;
                                                            }
                                                        }
                                                        if (customPrice != -1) {
                                                            cp = !yesNoConfirmation("Confirm price: " + customPrice + "? (Y/N)");
                                                        }
                                                    }
                                                    // if cp is still true, custom price was never set, exit this menu
                                                    if (cp) {
                                                        vec = false;
                                                        break;
                                                    }
                                                    // otherwise the custom price was set and confirmed
                                                    System.out.println("Custom price set: " + customPrice);
                                                    if (yesNoConfirmation("Confirm transaction completed? (Y/N)")) {
                                                        System.out.println("Transaction confirmed.");
                                                        Log cpLog = new Log(this.user.getEid(), vid, "Vehicle Charged Custom Price: " + customPrice + " and Closed.");
                                                        this.api.sendLogToDB(cpLog);
                                                        String cpJson = "{\"vid\": \"" + vid + "\", \"status\": \"closed\", \"paidAmount\": \"" + (customPrice + currentVehicle.getPaidAmount()) + "\"}";
                                                        this.api.updateVehicleClosed(cpJson);
                                                        vec = false;
                                                        break;
                                                    } else {
                                                        System.out.println("Transaction cancelled.  Exiting.");
                                                        vec = false;
                                                        break;
                                                    }
                                                case "3":
                                                    // comp
                                                    System.out.println("Please provide password: ");
                                                    String cpPw = scanner.nextLine();
                                                    if (!api.employeeLogIn(this.user.getEid(), cpPw) && this.user.getSystemAccess() < 2) {
                                                        System.out.println("Access denied.");
                                                        vec = false;
                                                        break;
                                                    }
                                                    System.out.println("Access granted.");
                                                    System.out.println("Please give reason for comping: ");
                                                    boolean compNotExplained = true;
                                                    String compReason;
                                                    while (compNotExplained) {
                                                        compReason = scanner.nextLine();
                                                        if (yesNoConfirmation("Confirm reason: " + compReason + " (Y/N)")) {
                                                            compNotExplained = false;
                                                        } else {
                                                            System.out.println("Please give reason for comping: ");
                                                        }
                                                    }
                                                    if (yesNoConfirmation("Comp and close ticket?  (Y/N)")) {
                                                        Log compLog = new Log(this.user.getEid(), vid, "Vehicle Comped: " + compReason + ", Closed");
                                                        this.api.sendLogToDB(compLog);
                                                        String compJson = "{\"vid\": \"" + vid + "\", \"status\": \"closed\"}";
                                                        this.api.updateVehicleClosed(compJson);
                                                        System.out.println("Comped and closed.");
                                                    } else {
                                                        System.out.println("Cancelled and exiting.");
                                                        vec = false;
                                                        break;
                                                    }
                                                case "E":
                                                    // exit
                                                    System.out.println("Exiting.");
                                                    vec = false;
                                                    break;
                                                default:
                                                    System.out.println("Invalid option " + vecOption);
                                                    vecFailCount++;
                                                    if (vecFailCount > 9) {
                                                        vec = false;
                                                        System.out.println("Too many invalid inputs.  Closing menu");
                                                    }
                                                    break;
                                            }
                                        }
                                        break;
                                    case "2":
                                        // will return
                                        break;
                                    case "E":
                                        // exit
                                        System.out.println("Exiting.");
                                        ve = false;
                                        break;
                                    default:
                                        System.out.println("Invalid option " + veOption);
                                        veFailCount++;
                                        if (veFailCount > 9) {
                                            ve = false;
                                            System.out.println("Too many invalid inputs.  Closing menu");
                                        }
                                        break;
                                }
                            }
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

    private boolean yesNoConfirmation(String words) {
        System.out.println(words);
        boolean res = false;
        boolean hasntAnswered = true;
        int failCount = 0;
        while (hasntAnswered) {
            String inp = scanner.nextLine();
            switch (inp) {
                case "Y":
                    res = true;
                    hasntAnswered = false;
                    break;
                case "N":
                    hasntAnswered = false;
                    break;
                case "E":
                    hasntAnswered = false;
                    break;
                default:
                    failCount++;
                    if (failCount > 9) {
                        hasntAnswered = false;
                    }
                    break;
            }
        }
        return res;
    }
}

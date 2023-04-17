package com.github.kadika38;

import java.security.KeyRep;
import java.util.ArrayList;
import java.util.Scanner;

public class POS2 {
    Garage garage;
    Employee user;
    APICallMaker api;
    Scanner scanner;
    Integer dailyRate;
    Integer hourlyRate;


    POS2(Garage garage, String url) {
        this.garage = garage;
        this.api = new APICallMaker(url);
        this.user = null;
        this.scanner = new Scanner(System.in);
        this.dailyRate = 0;
        this.hourlyRate = 0;
    }

    public void setRates(int dailyRate, int hourlyRate) {
        this.dailyRate = dailyRate;
        this.hourlyRate = hourlyRate;
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
            System.out.println("1) Vehicle Operations\n2) Employee Operations\n3) Logout\n4) Exit Menu");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    vehicleOpsMenu();
                    break;
                case 2:
                    employeeOpsMenu();
                    break;
                case 3:
                    logout();
                    keepRunning = false;
                    break;
                case 4:
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
                System.out.println("Saved.");
                keepRunning = false;
            } else if ("E".equals(res)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private void vehicleExitMenu() {
        boolean keepRunning = true;
        Integer choice = -1;
        while (keepRunning) {
            System.out.println("1) Close\n2) Will Return\n3) Exit Menu");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    closeVehicleMenu();
                    break;
                case 2:
                    willReturnMenu();
                    break;
                case 3:
                    keepRunning = false;
                    System.out.println("Exiting Menu.");
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }

    private void closeVehicleMenu() {
        boolean keepRunning = true;
        Integer choice = -1;
        while (keepRunning) {
            System.out.println("1) Normal Price\n2) Custom Price\n3) Comp\n4) Exit Menu");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    closeWithNormalPrice();
                    break;
                case 2:
                    closeWithCustomPrice();
                    break;
                case 3:
                    closeWithComp();
                    break;
                case 4:
                    keepRunning = false;
                    System.out.println("Exiting Menu.");
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }

    private void closeWithNormalPrice() {
        Vehicle v = getVehicleFromVIDInput();
        if (v == null) {
            return;
        }
        Integer hoursNotPaid = v.getHoursNotPaidFor();
        Integer price = ((hoursNotPaid % 24) * this.hourlyRate) + (((hoursNotPaid - (hoursNotPaid % 24)) / 24) * this.dailyRate);
        System.out.println("Customer owes " + price + " for " + hoursNotPaid " hours of unpaid parking.");
        if (confirmCollectionOfMoney(price)) {
            System.out.println("Transaction complete, closing vehicle.");
            Log log = new Log(this.user.getEid(), v.getVid(), "Vehicle Charged Normal Price and Closed - $" + price + " Collected");
            this.api.sendLogToDB(log);
            v.setStatus("Closed");
            v.setPaidAmount(v.getPaidAmount() + price);
            this.api.sendVehicleToDB(v);
            this.garage.vehicleExiting(v);
        } else {
            System.out.println("Transaction incomplete, vehicle is still open.  Closing menu.");
            return;
        }
    }

    private void closeWithCustomPrice() {
        if (this.user.getSystemAccess() <= 1) {
            System.out.println("System access level not high enough, function restricted.  Exiting.");
            return;
        } 
        Vehicle v = getVehicleFromVIDInput();
        if (v == null) {
            return;
        }
        Integer timeParked = v.getTotalTimeParked();
        System.out.println("Vehicle parked for total of " + timeParked + " hours and already paid $" + v.getPaidAmount());
        System.out.println("Input custom price, or 'E' to exit:");
        boolean keepRunning = true;
        Integer price;
        while (keepRunning) {
            String priceString = scanner.nextLine();
            if ("E".equals(priceString)) {
                System.out.println("Exiting.");
                return;
            }
            try {
                price = Integer.parseInt(priceString);
                keepRunning = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.")
            }
        }
        if (confirmCollectionOfMoney(price)) {
            System.out.println("Transaction complete, closing vehicle.");
            Log log = new Log(this.user.getEid(), v.getVid(), "Vehicle Charged Custom Price and Closed - $" + price + " Collected");
            this.api.sendLogToDB(log);
            v.setStatus("Closed");
            v.setPaidAmount(v.getPaidAmount() + price);
            this.api.sendVehicleToDB(v);
            this.garage.vehicleExiting(v);
        } else {
            System.out.println("Transaction incomplete, vehicle still open.  Closing menu.");
            return;
        }
    }

    private void closeWithComp() {
        if (this.user.getSystemAccess() <= 1) {
            System.out.println("System access level not high enough, function restricted.  Exiting.");
            return;
        }
        Vehicle v = getVehicleFromVIDInput();
        if (v == null) {
            return;
        }
        Integer timeParked = v.getTotalTimeParked();
        System.out.println("Vehicle parked for total of " + timeParked + " hours and already paid $" + v.getPaidAmount());
        System.out.println("Input reason for comp, or 'E' to exit:");
        boolean keepRunning = true;
        while (keepRunning) {
            String reason = scanner.nextLine();
            if (reason.length() > 0) {
                System.out.println("Confirm reason for comp is correct: 'Y' to confirm, 'E' to exit:");
                String confirm = scanner.nextLine();
                if ("E".equals(confirm)) {
                    System.out.println("Exiting.");
                    return;
                } else if ("Y".equals(confirm)) {
                    System.out.println("Confirmed.  Closing vehicle.");
                    keepRunning = false;
                    Log log = new Log(this.user.getEid(), v.getVid(), "Vehicle Comped and Closed: " + reason);
                    this.api.sendLogToDB(log);
                    v.setStatus("Closed");
                    this.api.sendVehicleToDB(v);
                    this.garage.vehicleExiting(v);
                } else {
                    System.out.println("Invalid input.");
                }
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private void willReturnMenu() {
        Vehicle v = getVehicleFromVIDInput();
        if (v == null) {
            return;
        }
        if (v.getRoomNumber() != null && v.getLicensePlate() != null && v.getLicensePlateState() != null) {
            System.out.println("Confirm vehicle exiting, 'Y' to confirm, 'E' to exit:");
            boolean keepRunning = true;
            while (keepRunning) {
                String confirm = scanner.nextLine();
                if ("E".equals(confirm)) {
                    System.out.println("Exiting.");
                    return;
                } else if ("Y".equals(confirm)) {
                    keepRunning = false;
                    System.out.println("Confirmed.  Vehicle exiting.");
                    Log log = new Log(this.user.getEid(), v.getVid(), "Vehicle Exiting.");
                    this.api.sendLogToDB(log);
                    v.setStatus("Requested");
                    this.api.sendVehicleToDB(v);
                    this.garage.vehicleExiting();
                } else {
                    System.out.println("Invalid input.");
                }
            }
        } else {
            System.out.println("To continue, more more information is required.  To exit at any time, input 'E'.");
            if (v.getLicensePlate() == null) {
                System.out.println("License Plate required.  Please enter vehicle's license plate:");
                boolean keepRunning = true;
                while (keepRunning) {
                    String lp = scanner.nextLine();
                    if ("E".equals(lp)) {
                        System.out.println("Exiting.");
                        return;
                    } else if (lp.length() > 1 && lp.length() < 9) {
                        v.setLicensePlate(lp);
                        keepRunning = false;
                    } else {
                        System.out.println("Invalid input.");
                    }
                }
            }
            if (v.getLicensePlateState() == null) {
                System.out.println("License Plate Origin required.  Please enter vehicle's license plate origin:");
                boolean keepRunning = true;
                while (keepRunning) {
                    String lps = scanner.nextLine();
                    if ("E".equals(lps)) {
                        System.out.println("Exiting.");
                        return;
                    } else if (lps.length() == 2 && Character.isLetter(lps.charAt(0)) && Character.isLetter(lps.charAt(1))) {
                        v.setLicensePlateState(lps);
                        keepRunning = false;
                    } else {
                        System.out.println("Invalid input.");
                    }
                }
            }
            if (v.getRoomNumber() == null) {
                System.out.println("Room Number required.  Please enter customer's room number:");
                boolean keepRunning = true;
                while (keepRunning) {
                    String rm = scanner.nextLine();
                    if ("E".equals(rm)) {
                        System.out.println("Exiting.");
                        return;
                    } else {
                        try {
                            Integer rmNum = Integer.parseInt(rm);
                            keepRunning = false;
                            v.setRoomNumber(rmNum);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                        }
                    }
                }
            }
            Log log = new Log(this.user.getEid(), v.getVid(), "Vehicle information updated.");
            this.api.sendLogToDB(log);
            System.out.println("Confirm vehicle exiting, 'Y' to confirm, 'E' to exit:");
            boolean keepRunning = true;
            while (keepRunning) {
                String confirm = scanner.nextLine();
                if ("E".equals(confirm)) {
                    System.out.println("Exiting.");
                    return;
                } else if ("Y".equals(confirm)) {
                    keepRunning = false;
                    System.out.println("Confirmed.  Vehicle exiting.");
                    Log log = new Log(this.user.getEid(), v.getVid(), "Vehicle Exiting.");
                    this.api.sendLogToDB(log);
                    v.setStatus("Requested");
                    this.api.sendVehicleToDB(v);
                    this.garage.vehicleExiting();
                } else {
                    System.out.println("Invalid input.");
                }
            }
        }
    }

    private void prepayMenu() {
        Vehicle v = getVehicleFromVIDInput();
        if (v == null) {
            return;
        }
        System.out.println("Prepaying.  Enter amount to prepay, or 'E' to exit:");
        boolean keepRunning = true;
        Integer prepay;
        while (keepRunning) {
            String prepayString = scanner.nextLine();
            if ("E".equals(prepayString)) {
                System.out.println("Exiting.");
                return;
            } else {
                try {
                    prepay = Integer.parseInt(prepayString);
                    keepRunning = false;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        }
        if (confirmCollectionOfMoney(prepay)) {
            v.setPaidAmount(v.getPaidAmount() + prepay);
            this.api.sendVehicleToDB(v);
            Log log = new Log(this.user.getEid(), v.getVid(), "Prepaid: $" + prepay);
            this.api.sendLogToDB(log);
            System.out.println("Transaction complete.");
        }
    }

    private void viewVehicleLogsMenu() {
        Vehicle v = getVehicleFromVIDInput();
        if (v == null) {
            return;
        }
        ArrayList<Log> logs = this.api.retrieveVehicleLogs(v.getVid());
        System.out.println("Vehicle logs:");
        for (Log log : logs) {
            log.print();
        }
        System.out.println("Input anything to return to Vehicle Operations Menu");
        String finished = scanner.nextLine();
        return;
    }

    private void employeeOpsMenu() {
        if (this.user.getSystemAccess() > 2) {
            boolean keepRunning = true;
            Integer choice = -1;
            while (keepRunning) {
                System.out.println("1) Add New Employee\n2) Edit Existing Employee\n3) Exit Menu");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addNewEmployee();
                        break;
                    case 2:
                        editExistingEmployeeMenu();
                        break;
                    case 3:
                        keepRunning = false;
                        System.out.println("Exiting Menu.");
                        break;
                    default:
                        System.out.println("Invalid input.");
                        break;
                }
            }
        } else {
            System.out.println("System access level too low.  Operation restricted.  Exiting.");
            return;
        }
    }

    private void addNewEmployee() {
        boolean keepRunning = true;
        String newEid;
        String name;
        Integer garageAccess;
        Integer systemAccess;
        Employee newEmployee;
        System.out.println("At any time, input 'E' to exit.");
        System.out.println("Enter new Employee ID:");
        while (keepRunning) {
            newEid = scanner.nextLine();
            if ("E".equals(newEid)) {
                System.out.println("Exiting.");
                return;
            } else if (Employee.isValidEmployeeID(newEid)) {
                newEmployee = new Employee(newEid);
                keepRunning = false;
            } else {
                System.out.println("Invalid Emploee ID.");
            }
        }
        keepRunning = true;
        System.out.println("Enter new Employee's name:");
        while (keepRunning) {
            name = scanner.nextLine();
            if ("E".equals(name)) {
                System.out.println("Exiting.");
                return;
            } else if (name.length() > 0) {
                newEmployee.setName(name);
                keepRunning = false;
            } else {
                System.out.println("Invalid input");
            }
        }
        keepRunning = true;
        System.out.println("Input Garage Access Level:\n0) No Access\n1) Basic Valet Access\n2) Master Access");
        while (keepRunning) {
            String ga = scanner.nextLine();
            if ("E".equals(ga)) {
                System.out.println("Exiting.");
                return;
            }
            try {
                garageAccess = Integer.parseInt(ga);
                newEmployee.setGarageAccess(garageAccess);
                keepRunning = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
        keepRunning = true;
        System.out.println("Input System Access Level:\n0) No Access\n1) Basic Valet Access\n2) Captain Access\n3) Master Access");
        while (keepRunning) {
            String sa = scanner.nextLine();
            if ("E".equals(sa)) {
                System.out.println("Exiting.");
                return;
            }
            try {
                systemAccess = Integer.parseInt(sa);
                newEmployee.setSystemAccess(systemAccess);
                keepRunning = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
        keepRunning = true;
        while (keepRunning) {
            System.out.println("Input temporary password:");
            String enteredPw = scanner.nextLine();
            if ("E".equals(enteredPw)) {
                System.out.println("Exiting.");
                return;
            } else if (enteredPw.length() > 0) {
                System.out.println("Confirm temporary password:");
                String confirmPw = scanner.nextLine();
                if ("E".equals(confirmPw)) {
                    System.out.println("Exiting.");
                    return;
                } else if (confirmPw.equals(enteredPw)) {
                    newEmployee.setPassword(confirmPw);
                    System.out.println("Confirmed.  Password saved.");
                    keepRunning = false;
                } else {
                    System.out.println("Password did not match.");
                }
            }
        }
        this.api.sendEmployeeToDb(newEmployee);
        Log log = new Log(this.user.getEid(), "Created New Employee: " + newEmployee.getEid());
        this.api.sendLogToDB(log);
        System.out.println("New Employee Creation Complete.");
    }

    private void editExistingEmployeeMenu() {
        boolean keepRunning = true;
        Integer choice = -1;
        Employee e = null;
        String eName;
        while (keepRunning) {
            if (e == null) {
                eName = "NONE";
            }
            System.out.println("Current employee: " + eName);
            System.out.println("1) Enter Employee ID of Current Employee\n2) Update Name\n3) Update Gargae Access\n4) Update System Access\n5) Update Password\n6) Save Changes\n7) Exit Menu");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    Employee probablyE = getEmployeeFromEIDInput();
                    if (probablyE != null) {
                        e = probablyE;
                        eName = e.getEid();
                    }
                    break;
                case 2:
                    String probablyName = getEmployeeNameInput();
                    if (probablyName != null) {
                        e.setName(probablyName);
                    }
                    break;
                case 3:
                    Integer probablyGA = getGarageAccessInput();
                    if (probablyGA != null) {
                        e.setGarageAccess(probablyGA);
                    }
                    break;
                case 4:
                    Integer probablySA = getSystemAccessInput();
                    if (probablySA != null) {
                        e.setSystemAccess(probablySA);
                    }
                    break;
                case 5:
                    String probablyPw = getPasswordInput();
                    if (probablyPw != null) {
                        e.setPassword(probablyPw);
                    }
                    break;
                case 6:
                    employeeUpdateFinalize(e);
                    break;
                case 7:
                    keepRunning = false;
                    System.out.println("Exiting Menu.");
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }

    private Employee getEmployeeFromEIDInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter Employee ID or input 'E' to exit:");
            String enteredEid = scanner.nextLine();
            if ("E".equals(enteredEid)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else if (Employee.isValidEmployeeID(enteredEid)) {
                // updating keepRunning isn't necessary here but it feels safer to have
                keepRunning = false;
                System.out.println("Retrieving employee data...");
                return this.api.retrieveEmployeeFromDB(enteredEid);
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private String getEmployeeNameInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter updated employee name or input 'E' to exit:");
            String enteredName = scanner.nextLine();
            if ("E".equals(enteredName)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else if (enteredName.length() > 1) {
                keepRunning = false;
                return enteredName;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private Integer getGarageAccessInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Garage access level info:\n0 = no access\n1 = basic valet access\n2 = master access\nEnter this employee's garage access level (0, 1, or 2) or 'E' to exit:");
            String enteredGA = scanner.nextLine();
            if ("E".equals(enteredGA)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else {
                try {
                    Integer ga = Integer.parseInt(enteredGA);
                    if (ga >= 0 && ga <= 2) {
                        keepRunning = false;
                        return ga;
                    } else {
                        System.out.println("Invalid input.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        }
    }

    private Integer getSystemAccessInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("System access level info:\n0 = no access\n1 = basic valet access\n2 = captain access\n3 = master access\nEnter this employee's system access level (0, 1, 2, or 3) or 'E' to exit:");
            String enteredSA = scanner.nextLine();
            if ("E".equals(enteredSA)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else {
                try {
                    Integer sa = Integer.parseInt(enteredSA);
                    if (sa >= 0 && sa <= 3) {
                        keepRunning = false;
                        return sa;
                    } else {
                        System.out.println("Invalid input.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        }
    }

    private String getPasswordInput() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Enter updated employee password or input 'E' to exit:");
            String enteredPw = scanner.nextLine();
            if ("E".equals(enteredPw)) {
                System.out.println("Exiting.");
                keepRunning = false;
                return null;
            } else if (Employee.isValidPassword(enteredPw)) {
                keepRunning = false;
                return enteredPw;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    private void employeeUpdateFinalize(Employee e) {
        System.out.println("Employee info unsaved:");
        e.printInfo();
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("Confirm and save? 'Y' to confirm, 'E' to exit:");
            String res = scanner.nextLine();
            if ("Y".equals(res)) {
                System.out.println("Saving changes...");
                this.api.sendEmployeeToDb(e);
                Log log = new Log(this.user.getEid(), "Employee information updated");
                this.api.sendLogToDB(log);
                System.out.println("Saved.");
                keepRunning = false;
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

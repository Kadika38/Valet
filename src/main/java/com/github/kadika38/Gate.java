package com.github.kadika38;

public class Gate {
    APICallMaker api;

    Gate(String url) {
        this.api = new APICallMaker(url);
    }

    public void scanIn(String vid, String eid) {
        boolean vidIsValid = Vehicle.isValidVehicleID(vid);
        boolean eidIsValid = Employee.isValidEmployeeID(eid);
        if (vidIsValid && eidIsValid) {
            Employee e = this.api.retrieveEmployeeFromDB(eid);
            Vehicle v = this.api.retrieveVehicleFromDB(vid);
            if (e.garageAccess > 0) {
                openEntryGateForOneVehicle();
                v.setStatus("Being Parked");
                v.setLastTimeParkedNow();
                this.api.sendVehicleToDB(v);
                Log log = new Log(eid, vid, "Gate entry");
                this.api.sendLogToDB(log);
            } else {
                System.out.println("No Garage Access, access not granted.  See Manager.");
                Log log = new Log(eid, vid, "Gate entry attempt, access not granted due to employee garage access level");
                this.api.sendLogToDB(log);
            }
        } else {
            if (!vidIsValid) {
                System.out.println("Invalid Vehicle ID");
            }
            if (!eidIsValid) {
                System.out.println("Invalid Employee ID");
            }
        }
    }

    public void scanIn(String eid) {
        if (Employee.isValidEmployeeID(eid)) {
            Employee e = this.api.retrieveEmployeeFromDB(eid);
            if (e.garageAccess > 1) {
                System.out.println("Override access granted.");
                openEntryGateForOneVehicle();
                Log log = new Log(eid, "Gate entry - override access");
                this.api.sendLogToDB(log);
            } else {
                System.out.println("No override access.  Access denied.  see Manager.");
                Log log = new Log(eid, "Gate entry attempt, override access not granted due to employee garage access level");
                this.api.sendLogToDB(log);
            }
        } else {
            System.out.println("Invalid Employee ID");
        }
    }

    public void scanOut(String vid, String eid) {
        boolean vidIsValid = Vehicle.isValidVehicleID(vid);
        boolean eidIsValid = Employee.isValidEmployeeID(eid);
        if (vidIsValid && eidIsValid) {
            Employee e = this.api.retrieveEmployeeFromDB(eid);
            Vehicle v = this.api.retrieveVehicleFromDB(vid);
            if (e.garageAccess > 0) {
                if ("Requested".equals(v.getStatus())) {
                    openExitGateForOneVehicle();
                    v.setStatus("Retrieved");
                    this.api.sendVehicleToDB(v);
                    Log log = new Log(eid, vid, "Gate exit");
                    this.api.sendLogToDB(log);
                } else {
                    System.out.println("Vehicle not requested, exit not granted.");
                }
            } else {
                System.out.println("No Garage Access, exit not granted.  See Manager.");
                Log log = new Log(eid, vid, "Gate exit attempt, exit not granted due to employee garage access level");
                this.api.sendLogToDB(log);
            }
        } else {
            if (!vidIsValid) {
                System.out.println("Invalid Vehicle ID");
            }
            if (!eidIsValid) {
                System.out.println("Invalid Employee ID");
            }
        }
    }

    public void scanOut(String eid) {
        if (Employee.isValidEmployeeID(eid)) {
            Employee e = this.api.retrieveEmployeeFromDB(eid);
            if (e.getGarageAccess() > 1) {
                System.out.println("Override exit granted.");
                openExitGateForOneVehicle();
                Log log = new Log(eid, "Gate exit - override access");
                this.api.sendLogToDB(log);
            } else {
                System.out.println("No override access.  Exit denied.  See manager.");
                Log log = new Log(eid, "Gate exit attempt, override exit not granted due to employee garage access level");
                this.api.sendLogToDB(log);
            }
        } else {
            System.out.println("Invalid Employee ID");
        }
    }

    private void openEntryGateForOneVehicle() {
        // fake function for the purposes of this project
        System.out.println("Opening Entry Gate");
    }

    private void openExitGateForOneVehicle() {
        // fake function for the purposes of this project
        System.out.println("Opening Exit Gate");
    }
}

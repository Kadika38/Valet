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

    }

    public void scanOut(String vid, String eid) {

    }

    public void scanOut(String eid) {

    }

    private void openEntryGateForOneVehicle() {

    }

    private void openExitGateForOneVehicle() {

    }
}

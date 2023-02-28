public class Log {
    String eid;
    String vid;
    String log;
    String timestamp;

    Log(String eid, String vid, String log) {
        if (Employee.isValidEmployeeID(eid)) {
            this.eid = eid;
        } else {
            this.eid = null;
        }
        if (Vehicle.isValidVehicleID(vid)) {
            this.vid = vid;
        } else {
            this.eid = null;
        }
        this.log = log;
        // create a new date, convert it to a string and assign it to this.timestamp
        var date = new java.util.Date();
        this.timestamp = "";
        this.timestamp += date;
    }

    Log(String eid, String log) {
        if (Employee.isValidEmployeeID(eid)) {
            this.eid = eid;
        } else {
            this.eid = null;
        }
        this.vid = null;
        this.log = log;
        // create a new date, convert it to a string and assign it to this.timestamp
        var date = new java.util.Date();
        this.timestamp = "";
        this.timestamp += date;
    }

    
}

package com.github.kadika38;

import java.util.Date;

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
        Date date = new java.util.Date();
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
        Date date = new java.util.Date();
        this.timestamp = "";
        this.timestamp += date;
    }

    // Methods

    public String toJson() {
        String s = "{";
        if (this.eid != null) {
            s += "\"eid\": \"" + this.eid + "\"";
        }
        if (this.vid != null) {
            if (this.eid != null) {
                s += ", ";
            }
            s += "\"vid\": \"" + this.vid + "\"";
        }
        if (this.vid != null || this.eid != null) {
            s += ", ";
        }
        s += "\"log\": " + this.log + "\"";
        s += ", \"timestamp\": \"" + this.timestamp + "\"";
        s += "}";

        return s;
    }

    public void print() {
        if (this.vid == null) {
            System.out.println("Employee " + this.eid + ", Info: " + this.log + " @" + this.timestamp);
        } else {
            System.out.println("Employee " + this.eid + ", Vehicle " + this.vid + ", Info: " + this.log + " @" + this.timestamp);
        }
    }
}

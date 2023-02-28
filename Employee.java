public class Employee {
    String eid;
    String name;
    Integer garageAccess;
    Integer systemAccess;
    String password;

    Employee(String eid) {
        if (isValidEmployeeID(eid)) {
            this.eid = eid;
        } else {
            this.eid = null;
        }
        this.name = null;
        this.garageAccess = null;
        this.systemAccess = null;
        this.password = null;
    }


    // Used in this class and available publicly for validating eid's
    // Employee ID's start with the letter E and are 8 characters long including the leading E
    public static boolean isValidEmployeeID(String s) {
        if (s.length() > 0 && s.charAt(0) == 'E' && s.length() == 7) {
            return true;
        } else {
            return false;
        }
    }
}

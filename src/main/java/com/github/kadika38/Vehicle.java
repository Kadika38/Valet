package com.github.kadika38;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;

public class Vehicle {
    String vid;
    String status;
    String licensePlate;
    String licensePlateState;
    String make;
    String color;
    String location;
    String guestFirstName;
    String guestLastName;
    String lastTimeParked;
    Integer totalPreviousTimeParked;
    Integer roomNumber;
    Integer paidAmount;

    Vehicle(String vid) {
        if (isValidVehicleID(vid)) {
            this.vid = vid;
        } else {
            this.vid = null;
        }
        this.status = null;
        this.licensePlate = null;
        this.licensePlateState = null;
        this.make = null;
        this.color = null;
        this.location = null;
        this.guestFirstName = null;
        this.guestLastName = null;
        this.lastTimeParked = null;
        this.totalPreviousTimeParked = null;
        this.roomNumber = null;
        this.paidAmount = null;
    }

    Vehicle(String json, boolean useJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.readTree(json);
            
            this.vid = responseNode.get("vid").asText();

            this.status = responseNode.get("status").asText();
            this.licensePlate = responseNode.get("licensePlate").asText();
            this.licensePlateState = responseNode.get("licensePlateState").asText();
            this.make = responseNode.get("make").asText();
            this.color = responseNode.get("color").asText();
            this.location = responseNode.get("location").asText();
            this.guestFirstName = responseNode.get("guestFirstName").asText();
            this.guestLastName = responseNode.get("guestLastName").asText();
            this.lastTimeParked = responseNode.get("lastTimeParked").asText();
            this.totalPreviousTimeParked = responseNode.get("totalPreviousTimeParked").asInt();
            this.roomNumber = responseNode.get("roomNumber").asInt();
            this.paidAmount = responseNode.get("paidAmount").asInt();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // Setter Methods

    public String setStatus(String s) {
        if (s == "In" || s == "Being Parked" || s == "Requested" || s == "Retrieved" || s == "Out" || s == "Closed") {
            this.status = s;
        }
        return s;
    }

    public String setLicensePlate(String s) {
        if (s.length() > 1 && s.length() < 9) {
            this.licensePlate = s;
        }
        return s;
    }

    public String setLicensePlateState(String s) {
        if (s.length() == 2 && Character.isLetter(s.charAt(0)) && Character.isLetter(s.charAt(1))) {
            this.licensePlateState = s;
        }
        return s;
    }

    public String setMake(String make) {
        this.make = make;
        return make;
    }

    public String setColor(String color) {
        this.color = color;
        return color;
    }

    public String setLocation(String s) {
        this.location = s;
        return s;
    }

    public String setGuestFirstName(String s) {
        this.guestFirstName = s;
        return s;
    }

    public String setGuestLastName(String s) {
        this.guestLastName = s;
        return s;
    }

    public String setLastTimeParked(String s) {
        this.lastTimeParked = s;
        return s;
    }

    public void setLastTimeParkedNow() {
        Date date = new java.util.Date();
        this.lastTimeParked = "";
        this.lastTimeParked += date;
    }

    public Integer setTotalPreviousTimeParked(Integer num) {
        this.totalPreviousTimeParked = num;
        return num;
    }

    public Integer setRoomNumber(Integer num) {
        this.roomNumber = num;
        return num;
    }

    public Integer setPaidAmount(Integer num) {
        this.paidAmount = num;
        return num;
    }

    // Getter Methods

    public String getVid() {
        return this.vid;
    }

    public String getStatus() {
        return this.status;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public String getLicensePlateState() {
        return this.licensePlateState;
    }

    public String getMake() {
        return this.make;
    }

    public String getColor() {
        return this.color;
    }

    public String getLocation() {
        return this.location;
    }

    public String getGuestFirstName() {
        return this.guestFirstName;
    }

    public String getGuestLastName() {
        return this.guestLastName;
    }

    public String getLastTimeParked() {
        return this.lastTimeParked;
    }

    public Integer getTotalPreviousTimeParked() {
        // in minutes
        return this.totalPreviousTimeParked;
    }



    public Integer getTotalTimeParked() {
        // in minutes
        Integer totalTimeParked = this.totalPreviousTimeParked;
        LocalDateTime ltp = LocalDateTime.parse(this.lastTimeParked);
        Date rn = new java.util.Date();
        Integer diff = (int) ChronoUnit.MINUTES.between(ltp, (Temporal) rn);
        totalTimeParked += diff;
        return totalTimeParked;     
    }

    public Integer getRoomNumber() {
        return this.roomNumber;
    }

    public Integer getPaidAmount() {
        return this.paidAmount;
    }

    // Other methods

    public String toJson() {
        String s = "{";
        s += "\"vid\": \"" + this.vid + "\"";
        if (this.status != null) {
            s += ", \"status\": \"" + this.status + "\"";
        }
        if (this.licensePlate != null) {
            s += ", \"license plate\": \"" + this.licensePlate + "\"";
        }
        if (this.licensePlateState != null) {
            s += ", \"license plate state\": \"" + this.licensePlateState + "\"";
        }
        if (this.make != null) {
            s += ", \"make\": \"" + this.make + "\"";
        }
        if (this.color != null) {
            s += ", \"color\": \"" + this.color + "\"";
        }
        if (this.location != null) {
            s += ", \"location\": \"" + this.location + "\"";
        }
        if (this.guestFirstName != null) {
            s += ", \"guest first name\": \"" + this.guestFirstName + "\"";
        }
        if (this.guestLastName != null) {
            s += ", \"guest last name\": \"" + this.guestLastName + "\"";
        }
        if (this.lastTimeParked != null) {
            s += ", \"last time parked\": \"" + this.lastTimeParked + "\"";
        }
        if (this.totalPreviousTimeParked != null) {
            s += ", \"total previous time parked\": \"" + this.totalPreviousTimeParked + "\"";
        }
        if (this.roomNumber != null) {
            s += ", \"room number\": \"" + this.roomNumber + "\"";
        }
        if (this.paidAmount != null) {
            s += ", \"paid amount\": \"" + this.paidAmount + "\"";
        }
        s += "}";

        return s;
    }

    public void printInfo() {
        System.out.println("Vehicle info:");
        System.out.println("Vehicle ID: " + this.vid);
        System.out.println("Make: " + this.make);
        System.out.println("Color: " + this.color);
        System.out.println("License Plate: " + this.licensePlate + " - " + this.licensePlateState);
        System.out.println("Location: " + this.location);
        System.out.println("Status: " + this.status);
        System.out.println("Guest Name: " + this.guestFirstName + " " + this.guestLastName);
        System.out.println("Guest's Room Number: " + this.roomNumber);
        System.out.println("Paid Amount: " + this.paidAmount);
        System.out.println("Total Time Parked: " + this.getTotalTimeParked() + " hours");
    }

    // Used in this class and available publicly for validating vid's
    // Vehicle ID's start with the letter V and are 8 characters long including the leading V
    public static boolean isValidVehicleID(String s) {
        if (s.length() > 0 && s.charAt(0) == 'V' && s.length() == 7) {
            return true;
        } else {
            return false;
        }
    }
}
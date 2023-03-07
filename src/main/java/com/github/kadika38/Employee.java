package com.github.kadika38;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    Employee(String json, boolean useJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.readTree(json);
            
            this.eid = responseNode.get("eid").asText();
            this.name = responseNode.get("name").asText();
            this.garageAccess = responseNode.get("garageAccess").asInt();
            this.systemAccess = responseNode.get("systemAccess").asInt();
            this.password = null;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // Setter methods

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public Integer setGarageAccess(Integer i) {
        if (i >= 0 && i <= 2) {
            this.garageAccess = i;
        }
        return i;
    }

    public Integer setSystemAccess(Integer i) {
        if (i >= 0 && i <= 3) {
            this.systemAccess = i;
        }
        return i;
    }

    public String setPassword(String pw) {
        if (isValidPassword(pw)) {
            this.password = pw;
        }
        return pw;
    }

    // Getter methods

    public String getEid() {
        return this.eid;
    }

    public String getName() {
        return this.name;
    }

    public Integer getGarageAccess() {
        return this.garageAccess;
    }

    public Integer getSystemAccess() {
        return this.systemAccess;
    }

    public String getPassword() {
        return this.password;
    }

    // Other methods

    public String toJson() {
        String s = "{";
        s += "\"eid\": \"" + this.eid + "\"";
        if (this.name != null) {
            s += ", \"name\": \"" + this.name + "\"";
        }
        if (this.garageAccess != null) {
            s += ", \"garage access\": \"" + this.garageAccess + "\"";
        }
        if (this.systemAccess != null) {
            s += ", \"system access\": \"" + this.systemAccess + "\"";
        }
        s += "}";

        return s;
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

    // Used for validating passwords
    // !!!!!!!! will be filled in more later
    public static  boolean isValidPassword(String s) {
        if (s.length() > 0) {
            return true;
        } else {
            return false;
        }
    }
}

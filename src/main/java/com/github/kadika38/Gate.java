package com.github.kadika38;

public class Gate {
    APICallMaker api;

    Gate(String url) {
        this.api = new APICallMaker(url);
    }

    public void scanIn(String vid, String eid) {

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

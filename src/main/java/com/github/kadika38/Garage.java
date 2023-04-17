package com.github.kadika38;

import java.util.ArrayList;
import java.util.Map;

public class Garage {
    Map<Spot, Vehicle> garage;
    ArrayList<Spot> spotList;

    Garage(ArrayList<Spot> spotList) {
        this.spotList = spotList;
        for (Spot spot : this.spotList) {
            this.garage.put(spot, null);
        }
    }

    public boolean hasSpot(String spotName) {
        for (Spot spot : this.spotList) {
            if (spotName.equals(spot.getName())) {
                return true;
            }
        }
        return false;
    }

    public void update(Vehicle v) {
        String spotName = v.getLocation();
        String vid = v.getVid();
        // find if vehicle was already in a spot in the garage, and remove it from that spot if so
        for (Spot spot : this.spotList) {
            if (this.garage.get(spot).getVid().equals(vid)) {
                this.garage.put(spot, null);
            }
        }
        // put vehicle in current spot
        for (Spot spot : this.spotList) {
            if (spotName.equals(spot.getName())) {
                this.garage.put(spot, v);
            }
        }
    }

    public ArrayList<Vehicle> vehicleExiting(Vehicle v) {
        String vid = v.getVid();
        ArrayList<Vehicle> blockers = new ArrayList<Vehicle>();
        for (Spot spot : this.spotList) {
            if (this.garage.get(spot).getVid().equals(vid)) {
                this.garage.put(spot, null);
                if (spot.getBlockedBy().size() > 0) {
                    for (Spot blocker : spot.getBlockedBy()) {
                        if (this.garage.get(blocker) != null) {
                            blockers.add(this.garage.get(blocker));
                        }
                    }
                }
                break;
            }
        }
        return blockers;
    }
}

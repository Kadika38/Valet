import java.util.Map;

public class Vehicle {
    private String licensePlate = null;
    private String licensePlateOrigin = null;
    private String make = null;
    private String color = null;
    private String guestLastName = null;
    private String guestFirstName = null;
    

    //map constructor
    public Vehicle(Map<String, String> params) {
        if (params.containsKey("licensePlate")) {
            this.licensePlate = params.get("licensePlate");
        }
        if (params.containsKey("licensePlateOrigin")) {
            this.licensePlateOrigin = params.get("licensePlateOrigin");
        }
        if (params.containsKey("make")) {
            this.make = params.get("make");
        }
        if (params.containsKey("color")) {
            this.color = params.get("color");
        }
        if (params.containsKey("guestLastName")) {
            this.guestLastName = params.get("guestLastName");
        }
        if (params.containsKey("guestFirstName")) {
            this.guestFirstName = params.get("guestFirstName");
        }

        System.out.println("Vehicle created from map");
    }

    //converts full vehicle info to json for input into db
    private String toJsonString() {
        boolean before = false;
        String s = "{";
        if (this.licensePlate != null) {
            s += "\"licensePlate\": ";
            s += "\"" + this.licensePlate + "\"";
            before = true;
        }
        if (this.licensePlateOrigin != null) {
            if (before) s += ", ";
            s += "\"licensePlateOrigin\": ";
            s += "\"" + this.licensePlateOrigin + "\"";
            before = true;
        }
        if (this.make != null) {
            if (before) s += ", ";
            s += "\"make\": ";
            s += "\"" + this.make + "\"";
            before = true;
        }
        if (this.color != null) {
            if (before) s += ", ";
            s += "\"color\": ";
            s += "\"" + this.color + "\"";
            before = true;
        }
        if (this.guestLastName != null) {
            if (before) s += ", ";
            s += "\"guestLastName\": ";
            s += "\"" + this.guestLastName + "\"";
            before = true;
        }
        if (this.guestFirstName != null) {
            if (before) s += ", ";
            s += "\"guestFirstName\": ";
            s += "\"" + this.guestFirstName + "\"";
            before = true;
        }
        s += "}";

        return s;
    }

    public void sendToDB(APICallMaker api) {
        api.newVehicleEntry(this.toJsonString());
    }
}

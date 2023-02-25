public class Vehicle {
    String vid;
    String status;
    String licensePlate;
    String licensePlateState;
    String make;
    String color;
    Spot location;
    String guestFirstName;
    String guestLastName;
    String lastTimeParked;
    Integer totalPreviousTimeParked;
    Boolean transientOnly;
    Integer roomNumber;
    Integer paidAmount;

    Vehicle(String vid) {
        if (isValidVehicleID(vid)) {
            this.vid = vid;
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

    public Spot setLocation(Spot s) {
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

    public Integer setTotalPreviousTimeParked(Integer num) {
        this.totalPreviousTimeParked = num;
        return num;
    }

    public boolean setTransientOnly(boolean b) {
        this.transientOnly = b;
        return b;
    }

    public Integer setRoomNumber(Integer num) {
        this.roomNumber = num;
        return num;
    }

    public Integer setPaidAmount(Integer num) {
        this.paidAmount = num;
        return num;
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
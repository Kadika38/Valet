import java.util.ArrayList;

public class Spot {
    String name;
    ArrayList<Spot> blockedBy;

    Spot(String name) {
        this.name = name;
        blockedBy = new ArrayList<Spot>();
    }

    public Spot setBlocking(Spot s) {
        s.setBlockedBy(this);
        return s;
    }

    private Spot setBlockedBy(Spot s) {
        this.blockedBy.add(s);
        return s;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Spot> getBlockedBy() {
        return this.blockedBy;
    }
}

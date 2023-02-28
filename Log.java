public class Log {
    String eid;
    String vid;
    String log;
    String timestamp;

    Log(String eid, String vid, String log) {
        if (Employee.isValidEmployeeID(eid)) {
            this.eid = eid;
        }
    }
}

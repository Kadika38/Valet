Trying to map out all necessary functions
* function still needs to be added

Notes / Todo
- POS
- During program initial set up, create all the garages spots ; then create a map of <Vehicle, Spot> called garage 

Notes for later
- find a way to validate make and color in Vehicle setter functions
- consider pros/cons of consolodating license plates and their states as a single license plate object, in Vehicle object
- later on, implement limited garage access for non valet employees

POS
(Primary Functions)


(Seconday Functions)

(Main) - this runs initial setup, and then once set up, simply runs the pos system *


-----------------------------

Gate
- (API Call Maker) api

Constructor (String url)
    - this.api = new API Call Maker (url)

(Functions)

- Scan in (Vehicle ID vid)
    - if vid is valid Vehicle ID
        - ask for input of Employee ID eid
        - if eid is valid Employee ID
            -> (call) (API Call Maker) Get Employee Garage Access
            - if employee garage access > 0
                - print "access granted"
                -> (call) Open Entry Gate for One Vehicle
                - create vehicle update json ~ {id: vid, status: being parked}
                -> (call) (API Call Maker) Update Vehicle Status (vehicle update json)
                - create new log(eid, vid, "Gate entry")
                -> (call) (API Call Maker) Send Log to DB (log)
            - if employee garage access == 0
                - print "no garage access, see manager"
                - create new log(eid, vid, "Gate entry attempt, access not granted due to employee garage access level")
                -> (call) (API Call Maker) Send Log to DB (log)

- Scan in (Employee ID eid) *
    - if eid is valid Employee ID
        -> (call) (API Call Maker) Get Employee Garage Access
        - if employee garage access > 1
            - print "override access granted"
            -> (call) Open Entry Gate for One Vehicle
            - create new log(eid, "Gate entry - override access")
            -> (call) (API Call Maker) Send Log to DB (log)
        - if employee garage access <= 1
            - print "no override access, access denied, see manager"
            - create new log(eid, "Gate entry atempt, override access not granted to to employee garage access level")
            -> (call) (API Call Maker) Send Log to DB (log)

- Scan out (Vehicle ID vid)
    - if vid is valid Vehicle ID
        -> (call) (API Call Maker) Get Vehicle Status
        - if vehicle status == "Requested"
            - ask for input of Employee ID eid
            - if eid is valid Employee ID
                -> (call) (API Call Maker) Get Employee Garage Access
                - if employee garage access > 0
                    - print "opening gate"
                    -> (call) Open Exit Gate for One Vehicle
                    - create vehicle update json ~ {id: vid, status: retrieved}
                    - create new log(eid, vid, "Gate exit")
                    -> (call) (API Call Maker) Send Log to DB (log)
                - if employee garage access == 0
                    - print "no garage access, see manager"
                    - create new log(eid, vid, "Gate exit attempt, not granted due to employee garage access level")
                    -> (call) (API Call Maker) Send Log to DB (log)

- Scan out (Employee ID eid)
    - if eid is valid Employee ID
        -> (call) (API Call Maker) Get Employee Garage Access
        - if employee garage access > 1
            - print "override access granted"
            -> (call) Open Exit Gate for One Vehicle
            - create new log(eid, "Gate exit - override access")
            -> (call) (API Call Maker) Send Log to DB (log)
        - if employee garage access <= 1
            - print "no override access, exit denied, see manager"
            - create new log(eid, "Gate exit atempt, override exit not granted to to employee garage access level")
            -> (call) (API Call Maker) Send Log to DB (log)

- Open Entry Gate for One Vehicle
    - this is a fake function for the purposes of this project, just print "entry gate opened for 1 vehicle"

- Open Exit Gate for One Vehicle Out
    - this is a fake function for the purposes of this project, just print "exit gate opened for 1 vehicle

(Main) - always actively waiting for scans in / out *

-----------------------------

Vehicle
- (String) ID
- (String) Status ("In" / "Being Parked" / "Requested" / "Retrieved" / "Out" / "Closed")
- (String) License Plate
- (String) License Plate State
- (String Make
- (String) Color
- (Spot) Location
- (String) Guest First Name
- (String) Guest Last Name

Constructor (ID)
    - this.ID = ID

(Functions - Public)

- Set Status (String s)
    - if s is valid status
    - this.status = s

- Set License Plate (String s)
    - if s is valid license plate
    - this.licensePlate = s

- Set License Plate State (String s)
    - if s is valid license plate state
    - this.licensePlateState = s

- Set Make (String s)
    - this.make = s

- Set Color (String s)
    - this.color = s

- Set Location (Spot s)
    - this.location = s;

- Set Guest First Name (String s)
    - if s is valid name
    - this.guestFirstName = s

- Set Guest Last Name (String s)
    - if s is valid name
    - this.guestLastName = s

- Get Status
    - return this.status

- Get License Plate
    - return this.licensePlate

- Get License Plate State
    - return this.licensePlateState

- Get Make
    - return this.make

- Get Color
    - return this.color

- Get Location
    - return this.location

- Get Guest First Name
    - return this.guestFirstName

- Get Guest Last Name
    - return this.guestLastName

- To json
    - piece together all attributes as json

-----------------------------

Employee *
- (String) ID
- (String) name
- (Integer) garage access (0 == no access / 1 == basic valet access / 2 == master access)

Constructor(ID)
    - this.ID = ID

(Functions)

- Set Name (String name)
    - this.name = name

- Set Garage Access (Integer i)
    - this.garageAccess = i;

- Get Name
    - return this.name

- Get Garage Access
    - return this.garageAccess

-----------------------------

Log
- (Employee ID) eid
- (Vehicle ID) vid
- (String) log
- (String) timestamp

Constructor (Employee ID eid, Vehicle ID vid, String s)
    - this.eid = eid
    - this.vid = vid
    - this.log = s
    - this.timestamp = make a timestamp of the current time

(Functions)

- To Json
    - convert log to json
    - return json


-----------------------------

Spot
- (String) name
- (Spot array) blocking

Constructor (String name)
    - this.name = name

- Blocking (Spot s)
    - add s to this.blocking


-----------------------------

API Call Maker
- url

Constructor (api url)
    - this.url = url

(Functions)

- Send Vehicle to DB (Vehicle v)
    - s = v.toJson
    -> send s to db
    - return success or failure

- Update Vehicle Status (String json)
    -> send json to db using route /vehicle/status
    - return success or failure

- Send Log to DB (Log log)
    - s = log.toJson
    -> send s to db
    - return success or failure

- Retrieve Vehicle from DB *
    - retrieve data
    -> (call) (Vehicle) Constructor (that data)
    - return Vehicle

- Get Employee Garage Access (Employee ID eid)
    - retrieve employee data using eid
    - return employee.garageAccess

- Get Vehicle Status (Vehicle ID vid)
    - retrieve vehicle data using vid
    - return vehicle.status

- Retrieve Vehicle Logs from DB (Vehicle) *
    - retrieve data
    - instantiate empty array
    -> for each log
    -> (call) (Vehicle Log) Constructor (that data)
    -> add Vehicle Log to array
    - return array of Vehicle Logs 
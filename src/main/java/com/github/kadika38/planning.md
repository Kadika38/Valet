Trying to map out all necessary functions
* function still needs to be added

Notes / Todo
- currently working on POS / vehicle ops / vehicle exit / close / custom price

Notes for later
- find a way to validate make and color in Vehicle setter functions
- consider pros/cons of consolodating license plates and their states as a single license plate object, in Vehicle object
- later on, implement limited garage access for non valet employees
- cashier reports
- find a way to make adjusting prices possible without changing the code base - make it a feature
- might want to validate dates in the Vehicle setLastTimeParked setter method
- add more validation to passwords for employees
- consider modifying the Log class setup to be a parent class with two children so that timestamp generation doesn't have to be rewritten for both types

POS
- (Map<Spot, Vehicle>) garage;
- (API Call Maker) api;
- (Employee) User

Constructor(Map<Spot, Vehicle> g, String url)
    - this.garage = g;
    - this.api = new API Call Maker(url)

Set Active User(Employee ID eid, String pw)
    -> (call) (API Call Maker) Login
    - if success
        -> (call) (API Call Maker) Retrieve Employee from DB
        - this.User = employee
    - else print "incorrect credentials, login failure"

(Functions)

- Vehicle Operations (Vehicle ID vid)
    - if vid is valid Vehilce ID
    -> (call) (API Call Maker) Retrieve Vehicle from DB
    - offer options:
        1. Update Vehicle Information
        2. Vehicle Exit
        3. Prepay
        4. View Vehicle Logs
        - switch (option chosen)
            Option 1:
                - offer to update vehicle info
                - if vehicle status is being parked
                    - update to in
                - if changes are confirmed
                    -> (call) (API Call Maker) Send Vehicle to DB
                    - create new log(eid, vid, "Vehicle Update: ~all vehicle change info~")
                    -> (call) (API Call Maker) Send Log to DB
                - if confirmed changes included a new parking spot
                    - if it was in a parking spot before & new spot is not taken by another vehicle
                        - garage.replace(old spot, null)
                        - garage.replace(new spot, vid)
                    - else if it was not in a parking spot before & new spot is not taken by another vehicle
                        - garage.replace(new spot, vid)
            Option 2:
                - offer options
                    A. Close
                    B. Will Return
                    - switch (option chosen)
                        Option A:
                            - offer options:
                                a. Normal Price
                                b. Custom Price
                                c. Comp
                                - switch (option chosen)
                                    Option a:
                                        - calculate price based on vehicle.lastTimeParked, vehicle.paidAmount, and vehicle.totalPreviousTimeParked
                                        - confirm transaction
                                        - create new log(eid, vid, "Vehicle Charged Normal Price: ~price~ and closed")
                                        -> (call) (API Call Maker) Send Log to DB
                                        - create new vehicle update json ~ {id: vid, status: closed, paid amount: new paid amount}
                                        -> (call) (API Call Maker) Update Vehicle Closed
                                        - if vehicle.spot.getBlockedBy isn't empty and any spot in it has a vehicle
                                            - print "blocked by ~those vehicles~"
                                    Option b:
                                        - require Employee to log in
                                        -> (call) (API Call Maker) Employee Log In
                                        - if successful
                                            -> (call) (API Call Maker) Get Employee System Access
                                                - if employee access is > 1
                                                    - take in custom price
                                                    - confirm custom price
                                                    - confirm transaction
                                                    - create new log(eid, vid, "Vehicle Charged Custom Price: ~price~ and closed")
                                                    -> (call) (API Call Maker) Send Log to DB
                                                    - create new vehicle update json ~ {id: vid, status: closed, paid amount: new paid amount}
                                                    -> (call) (API Call Maker) Update Vehicle Closed
                                                    - if vehicle.spot.getBlockedBy isn't empty and any spot in it has a vehicle
                                                        - print "blocked by ~those vehicles~"
                                    Option c:
                                        - require Employee to log in
                                        -> (call) (API Call Maker) Employee Log In
                                        - if successful
                                            - offer options for comp
                                            - confirm
                                            - create new log(eid, vid, "Vehicle Comped: ~reason~ - and closed")
                                            -> (call) (API Call Maker) Send Log to DB
                                            - create new vehicle update json ~ {id: vid, status: closed}
                                            -> (call) (API Call Maker) Update Vehicle Status
                                            - if vehicle.spot.getBlockedBy isn't empty and any spot in it has a vehicle
                                                - print "blocked by ~those vehicles~"
                        Option B:
                            - if vehicle is not transient && vehicle has license plate & room number
                                - confirm action
                                - create new log(eid, vid, "Vehicle Exiting")
                                -> (call) (API Call Maker) Send Log to DB
                                - create new vehicle update json ~ {id: vid, status: requested}
                                -> (call) (API Call Maker) Update Vehicle Status
                            - else
                                - take in license plate and/or room number
                                - create new log(eid, vid, "Vehicle Update: ~change info~")
                                -> (call) (API Call Maker) Send Log to DB
                                - create vehicle update json ~ {id: vid, status: requested, license plate: lp, room number: room}
                                -> (call) (API Call Maker) Update Vehicle Exiting
                                - create new log(eid, vid, "Vehicle Exiting")
                                -> (call) (API Call Maker) Send Log to DB
            Option 3:
                - take in prepay amount
                - confirm amount
                - confirm transaction
                - create new log(eid, vid, "Prepaid: ~amount~")
                -> (call) (API Call Maker) Send Log to DB
                - create vehicle update json ~ {id: vid, paid amount: new paid amount}
                -> (call) (API Call Maker) Update Vehicle Paid Amount
            Option 4:
                -> (call) (API Call Maker) Retrieve Vehicle Logs
                - show vehicle logs

- Employee Operations
    - have employee login
    -> (call) (API Call Maker) Employee Login
    - if succesful login
        -> (call) (API Call Maker) Get Employee System Access
        - if system access > 2
            - offer options:
                1. Add New Employee
                2. Edit Existing Employee
                - switch (chosen option)
                    Option 1:
                        - take in employee info
                        -> employee constructor
                        -> (call) (API Call Maker) Send Employee to DB
                        - create new log(eid, "Created new employee ~new employee id~")
                        -> (call) (API Call Maker) Send Log to DB
                    Option 2:
                        - take in employee id eid
                        -> (call) (API Call Maker) Retrieve Employee from DB
                        - show employee info (show password as protected, since we won't retrieve it from the db)
                        - take in changes
                        - set on employee instance using setter methods
                        - confirm final employee info
                        -> (call) (API Call Maker) Send Employee to DB
                        - create new log(eid, "Edited Employee: ~eid~")
                        -> (call) (API Call Maker) Send Log to DB


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
                - create vehicle update json ~ {id: vid, status: being parked, last time parked: ~now~}
                -> (call) (API Call Maker) Update Vehicle Garage Entry (vehicle update json)
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
- (String) Make
- (String) Color
- (Spot) Location
- (String) Guest First Name
- (String) Guest Last Name
- (String) Last parked
- (Integer) total previous time parked
- (Boolean) Transient
- (Integer) Room Number
- (Integer) Paid Amount

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

- Set Last Time Parked (String s)
    - if s is valid timestamp
    - this.lastTimeParked = s

- Set Total Previous Time Parked (Integer i)
    - if i is valid amount of time
    - this.totalPreviousTimeParked = i

- Set Transient (Boolean b)
    - this.transient = b

- Set Room Number (Integer i)
    - if i is valid room number
    - this.roomNumber = i

- Set Paid Amount (Integer i)
    - if i is valid dollar amount
    - this.paidAmount = i

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

- Get Last Time Parked
    - return this.lastTimeParked

- Get Total Previous Time Parked
    - return this.totalPreviousTimeParked

- Get Transient
    - return this.transient

- Get Room Number
    - return this.roomNumber

- Get Paid Amount
    - return this.getPaidAmount

- To json
    - piece together all attributes as json

-----------------------------

Employee
- (String) ID
- (String) name
- (Integer) garage access (0 == no access / 1 == basic valet access / 2 == master access)
- (Integer) system access (0 == no access / 1 == basic valet access / 2 == captain access / 3 == master access)
- (String) password

Constructor(ID)
    - this.ID = ID

(Functions)

- Set Name (String name)
    - this.name = name

- Set Garage Access (Integer i)
    - this.garageAccess = i;

- Set System Access (Integer i)
    - this.systemAccess = i

- Set Password (String s)
    - if s is valid password
    - this.password = s

- Get Name
    - return this.name

- Get Garage Access
    - return this.garageAccess

- Get System Access
    - return this.systemAccess

- Get Password
    - return this.password

- To Json
    - convert to json WITHOUT password attribute

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

Constructor (Employee ID eid, String s)
    - this.eid = eid
    - this.log = s
    - this.timestamp = make a timestamp of the current time

(Functions)

- To Json
    - convert log to json
    - return json


-----------------------------

Spot
- (String) name
- (Spot array) blocked by

Constructor (String name)
    - this.name = name

- Set Blocking (Spot s)
    - s.setBlockedBy(this)

- Set Blocked By (Spot s)
    - add s to this.blockedBy

- Get Blocked By
    - new array a
    - add spots in this.blockedBy to a
    - for each spot s in a
        - add s.getBlockedBy to a, if s.getBlockedBy isn't empty
    - return a

- Get Name
    - return this.name


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
    // route used for updating status only
    -> send json to db using route /vehicle/status
    - return success or failure

- Update Vehicle Gargage Entry (String json)
    //route used for updating status and last time parked at the same time
    -> send json to db using route /vehicle/ge
    - return success or failure

- Update Vehicle Closed (String json)
    //route used for updating status and paid amount
    -> send json to db using route /vehicle/closure
    - return success or failure

- Update Vehicle Exiting (String json)
    //route used for updating vehicles license plate and room number as well as status
    -> send json to db using route /vehicle/exit
    - return success or failure

- Update Vehicle Paid Amount (String json)
    //route used for updating the paid amount attribute of a vehicle
    -> send json to db using route /vehicle/pa
    - return success or failure

- Retrieve Vehicle from DB
    - retrieve data
    -> (call) (Vehicle) Constructor (that data)
    - return Vehicle

- Get Vehicle Status (Vehicle ID vid)
    - retrieve vehicle data using vid
    - return vehicle.status

- Send Employee to DB (Employee e)
    - p = e.getPassword
    - s = e.tojson
    -> send s to db using route /employee
    -> send p to db using route /employee/pw

- Retrieve Employee from DB (Employee ID eid)
    - retrieve employee info (not password though)
    - create new employee
    - append all info using setter methods
    - return employee

- Get Employee Garage Access (Employee ID eid)
    - retrieve employee data using eid
    - return employee.garageAccess

- Get Employee System Access (Employee ID eid)
    - retrieve employee data using eid
    - return employee.systemAccess

- Employee Log In (Employee ID eid, String pw)
    - GET route
    - send eid and pw to route /employee/login
    - return boolean response (login success or failure)

- Send Log to DB (Log log)
    - s = log.toJson
    -> send s to db
    - return success or failure

- Retrieve Vehicle Logs (Vehicle ID vid)
    -> retrieve logs only pertaining to vid
    - return array of logs
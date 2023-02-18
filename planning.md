Trying to map out all necessary functions
* function still needs to be added

Notes / Todo
- (POS) Add New Vehicle & Update Vehicle are very similar, put similar call blocks into new nested secondary function
- Next up, add Employees to the mix
- Still need more details

POS
(Primary Functions)
- Vehicle Enter
    - New car?
        -> (call) Add New Vehicle
    - Currently open car?
        -> (call) Update Vehicle
- Vehicle Exit
    -> (call) (API Call Maker) Retrieve Vehicle from DB
    - calculate cost of parking
    - facilitate transaction
    -> (call) (Vehicle) Update Vehicle
    -> (call) (API Call Maker) Send Vehicle to DB
    -> (call) (Vehicle Log) Constructor (update to existing vehicle)
    -> (call) (API Call Maker) Send Vehicle Log to DB
- View Vehicle Info *
    -> (call) (API Call Maker) Retrieve Vehicle from DB
    -> (call) (API Call Maker) Retrieve Vehicle Logs from DB *
    - Show to user
- Update Vehicle Info
    -> (call) Update Vehicle

(Seconday Functions)
- Add New Vehicle
    - Take in new vehicle info
        -> (call) (Vehicle) Vehicle Constructor
        -> (call) (API Call Maker) Send Vehicle to DB
        -> (call) (Vehicle Log) Constructor (New Vehicle)
        -> (call) (API Call Maker) Send Vehicle Log to DB
    - report success or failure
- Update Vehicle
    - retrieve vehicle info
        -> (call) (API Call Maker) Retrieve Vehicle from DB
    - take in what changes are to be made
    -> (call) (Vehicle) Update Vehicle
    -> (call) (API Call Maker) Send Vehicle to DB
    -> (call) (Vehicle Log) Constructor (update to existing vehicle)
    -> (call) (API Call Maker) Send Vehicle Log to DB
    - report success or failure

-----------------------------

Vehicle
(Functions)
- Constructor
    -> take in data
    -> instantiate new Vehicle
- Update Vehicle
    ~~ Multiple simple funcs

-----------------------------

Vehicle Log
(Functions)
- Constructor
    - New Vehicle?
        -> record new vehicle data to string
    - Already open vehicle?
        -> record change to string

-----------------------------

API Call Maker
(Functions)
- Send Vehicle to DB
- Send Vehicle Log to DB
- Retrieve Vehicle from DB
    - retrieve data
    -> (call) (Vehicle) Constructor
    - return Vehicle
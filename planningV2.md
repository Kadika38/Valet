Trying to map out all necessary functions
* function still needs to be added

Notes / Todo
- currently building out the vehicle class

POS
(Primary Functions)


(Seconday Functions)


-----------------------------

Gate
(Functions)

Scan in

Scan out

-----------------------------

Vehicle
- ID
- Status (In / Being Parked / Requested / Retrieved / Out / Closed)
- License Plate
- License Plate State
- Make
- Color
- Location
- Guest First Name
- Guest Last Name

Constructor (ID)
    - this.ID = ID

(Functions - Public)

- Set Status (String s)
    - if s is valid status
    - this.status = s

- Set Licen

- Set 

-----------------------------

Vehicle Log
(Functions)

-----------------------------

Employee
(Functions)

-----------------------------

Employee Log

-----------------------------

Log


-----------------------------

API Call Maker
- url

Constructor (api url)
    - this.url = url

(Functions)

- Send Vehicle to DB (Vehicle)
    - translate vehicle to json
    -> send json vehicle to db
    - return success or failure

- Send Vehicle Log to DB (Vehicle Log)
    - translate vehicle log to json
    -> send json vehicle log to db
    - return success or failure

- Retrieve Vehicle from DB
    - retrieve data
    -> (call) (Vehicle) Constructor (that data)
    - return Vehicle

- Retrieve Vehicle Logs from DB (Vehicle)
    - retrieve data
    - instantiate empty array
    -> for each log
    -> (call) (Vehicle Log) Constructor (that data)
    -> add Vehicle Log to array
    - return array of Vehicle Logs 
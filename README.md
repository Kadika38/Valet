Full readme will be added later.

Basic Overview:

Right now the goal is that App.java runs a simulation of two systems (the POS and the Gate) working simultaneously.  These represent the Point of Sale system for a Valet operation and the gate(s) at the entry/exit of the garage / parking lot.  This system as a whole manages and tracks all relevant data, keeping track of vehicles, employees, and keeping useful logs of system events.  

Both the POS and the Gate(s) are sending requests and retrieving data from an AWS DynamoDB.  The process of sending and retrieving from the DB uses two other AWS tools: an API Gateway and multiple Lamba Functions.  In short, the POS and Gate systems send requests to the API Gateway, which is integrated with Lambda functions.  These functions will make commands directly to the DynamoDB, and return the responses to the inquiring system.

Learning Goals:
    - Java experience
    - Maven experience
    - AWS Experience
        - DynamoDB
        - API Gateways
        - Lambda Functions
        - Integrations of multiple tools
    - OOP System Design & Development
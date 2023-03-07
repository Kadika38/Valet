package com.github.kadika38;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APICallMaker {
    String url;
    HttpClient client;

    APICallMaker(String url) {
        this.url = url;
        client = HttpClient.newHttpClient();
    }

    private HttpRequest createPutRequest(String json, String path) {
        HttpRequest request = HttpRequest.newBuilder()
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .uri(URI.create(this.url + path))
            .build();
        
        return request;
    }

    private HttpRequest createGetRequest(String path) {
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .header("Content-Type", "application/json")
            .uri(URI.create(this.url + path))
            .build();
        
        return request;
    }

    private boolean sendJsonToDB(String json, String path) {
        try {

            HttpRequest request = createPutRequest(json, path);

            //send request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String sendGetRequestToDB(String path) {
        try {

            HttpRequest request = createGetRequest(path);

            //send request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return "{}";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public boolean sendVehicleToDB(Vehicle v) {
        String json = v.toJson();
        return sendJsonToDB(json, "/vehicle/update/new");
    }

    public boolean updateVehicleStatus(String json) {
        return sendJsonToDB(json, "/vehicle/update/status");
    }

    public boolean updateVehicleGarageEntry(String json) {
        return sendJsonToDB(json, "/vehicle/update/ge");
    }

    public boolean updateVehicleClosed(String json) {
        return sendJsonToDB(json, "/vehicle/update/closure");
    }

    public boolean updateVehicleExiting(String json) {
        return sendJsonToDB(json, "/vehicle/update/exit");
    }

    public boolean updateVehiclePaidAmount(String json) {
        return sendJsonToDB(json, "/vehicle/update/pa");
    }

    public Vehicle retrieveVehicleFromDB(String vid) {
        if (Vehicle.isValidVehicleID(vid)) {
            String p = "/vehicle/find/" + vid;

            String response = sendGetRequestToDB(p);

            Vehicle v = new Vehicle(response, true);
            return v;
        }

        Vehicle failed = new Vehicle("");
        return failed;
    }

    public String getVehicleStatus(String vid) {
        if (Vehicle.isValidVehicleID(vid)) {
            String p = "/vehicle/find/" + vid;

            String response = sendGetRequestToDB(p);
            String status = "";

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseNode = mapper.readTree(response);
                status = responseNode.get("status").asText();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return status;
        }

        return null;
    }

    public boolean sendEmployeeToDb(Employee e) {
        String p = e.getPassword();
        String pwJson = "{\"pw\": \"" + p + "\"}";
        String json = e.toJson();
        String eid = e.getEid();

        Boolean a = sendJsonToDB(json, "/employee");
        Boolean b = sendJsonToDB(pwJson, "/employee/pw/" + eid);

        return a && b;
    }

    public Employee retrieveEmployeeFromDB(String eid) {
        String response = sendGetRequestToDB("/employee/find/" + eid);

        Employee e = new Employee(response, true);

        return e;
    }

    public Integer getEmployeeGarageAccess(String eid) {
        String response = sendGetRequestToDB("/employee/find/" + eid);
        Integer access = 0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.readTree(response);
            access = responseNode.get("garageAccess").asInt();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return access;
    }

    public Integer getEmployeeSystemAccess(String eid) {
        String response = sendGetRequestToDB("/employee/find/" + eid);
        Integer access = 0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.readTree(response);
            access = responseNode.get("systemAccess").asInt();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return access;
    }

    public boolean employeeLogIn(String eid, String pw) {
        String json = "{\"pw\": \":" + pw + "\"}";

        return sendJsonToDB(json, "/employee/login/" + eid);
    }

    public boolean sendLogToDB(Log log) {
        String json = log.toJson();
        return sendJsonToDB(json, "/log");
    }

    public ArrayList<Log> retrieveVehicleLogs(String vid) {
        //String response = sendGetRequestToDB("/log/vehicle/" + vid);
        //need to test working with multiple object responses

        //delete late
        return new ArrayList<Log>();
    }


}

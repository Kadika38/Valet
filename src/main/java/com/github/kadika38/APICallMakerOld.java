package com.github.kadika38;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APICallMakerOld {
    String url;

    String testInput = "{\"licensePlate\": \"test\"}";
    
    public APICallMakerOld(String url) {
        this.url = url;
    }

    public void newVehicleEntry(String vehicleData) {
        //create http client
        HttpClient client = HttpClient.newHttpClient();

        try {

            //create request
            HttpRequest request = HttpRequest.newBuilder()
            .PUT(HttpRequest.BodyPublishers.ofString(vehicleData))
            .header("Content-Type", "application/json")
            .uri(URI.create(this.url + "/vehicle"))
            .build();

            //send request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //print response
            System.out.println("Status code: " + response.statusCode());                            
            System.out.println("Headers: " + response.headers().allValues("content-type"));
            System.out.println("Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void requestTest() {
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .header("Content-Type", "application/json")
            .uri(URI.create(this.url + "/vehicle/all"))
            .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //System.out.println("Status code: " + response.statusCode());                            
            //System.out.println("Headers: " + response.headers().allValues("content-type"));
            //System.out.println("Body: " + response.body());

            //work with json here
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.readTree(response.body());
            for (JsonNode testVehicle : responseNode) {
                System.out.println(testVehicle.get("make").asText());
            }

            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

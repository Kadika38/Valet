import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
            var request = HttpRequest.newBuilder()
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
            var request = HttpRequest.newBuilder()
            .GET()
            .header("Content-Type", "application/json")
            .uri(URI.create(this.url + "/vehicle/find/" + "test"))
            .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status code: " + response.statusCode());                            
            System.out.println("Headers: " + response.headers().allValues("content-type"));
            System.out.println("Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

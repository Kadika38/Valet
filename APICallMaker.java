import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APICallMaker {
    String url;
    HttpClient client;

    APICallMaker(String url) {
        this.url = url;
        client = HttpClient.newHttpClient();
    }

    private HttpRequest createRequest(String json, String path) {
        var request = HttpRequest.newBuilder()
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .header("Content-Type", "application/json")
            .uri(URI.create(this.url + path))
            .build();
        
        return request;
    }

    private boolean sendJsonToDB(String json, String path) {
        try {

            HttpRequest request = createRequest(json, path);

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

        }
    }


}

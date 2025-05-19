package bazaar.internal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class BazaarApiClient {
    private static final String API_URL = "https://api.hypixel.net/v2/skyblock/bazaar";

    public JsonObject fetchRawData() throws Exception {
        URL url = new URL(API_URL);
        URLConnection connection = url.openConnection();
        connection.connect();

        return JsonParser.parseReader(new InputStreamReader(connection.getInputStream()))
                .getAsJsonObject();
    }
}
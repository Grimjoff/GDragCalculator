package bazaar;

import com.google.gson.JsonObject;
import bazaar.internal.BazaarApiClient;
import bazaar.internal.BazaarJsonParser;
import bazaar.model.BazaarProduct;

public class BazaarDataFetcher {

    private final BazaarApiClient apiClient = new BazaarApiClient();
    private final BazaarJsonParser jsonParser = new BazaarJsonParser();

    public BazaarProduct getProduct(String productId) {
        try {
            JsonObject json = apiClient.fetchRawData();
            return jsonParser.parseProductFromJson(json, productId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

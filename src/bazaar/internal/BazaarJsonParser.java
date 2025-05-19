package bazaar.internal;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import bazaar.model.BazaarProduct;

public class BazaarJsonParser {
    public BazaarProduct parseProductFromJson(JsonObject fullJson, String productId) {
        if (!fullJson.has("products")) return null;
        JsonObject products = fullJson.getAsJsonObject("products");
        JsonObject productJson = products.getAsJsonObject(productId.toUpperCase());

        if (productJson == null) return null;

        return new Gson().fromJson(productJson, BazaarProduct.class);
    }
}

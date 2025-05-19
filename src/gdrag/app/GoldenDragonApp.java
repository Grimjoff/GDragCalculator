package gdrag.app;

import bazaar.BazaarDataFetcher;
import bazaar.model.BazaarProduct;
import com.google.gson.*;
import gdrag.model.CheapGDrag;
import gdrag.gui.OutputWindow;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoldenDragonApp {

    private static OutputWindow outputWindow;

    // Static constants for calculations
    private static final double BASE_PRICE = 580_000_000;


    // Dynamic prices from Bazaar
    private static double minionLootDaily;
    private static double priceMaxLevel = 1_069_000_000;
    private static double totalPages = 10;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            outputWindow = new OutputWindow("Auctions");
            outputWindow.setVisible(true);
            try {
                runAppLogic();
            } catch (IOException e) {
                outputWindow.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private static void runAppLogic() throws IOException {
        BazaarDataFetcher fetcher = new BazaarDataFetcher();

        BazaarProduct rottenFlesh = fetcher.getProduct("ENCHANTED_ROTTEN_FLESH");
        BazaarProduct diamond = fetcher.getProduct("ENCHANTED_DIAMOND");

        double rottenFleshPrice = rottenFlesh.getQuick_status().getSellPrice();
        double diamondPrice = diamond.getQuick_status().getSellPrice();
        minionLootDaily = 2_916_777 * (rottenFleshPrice / 160) + 194_451 * diamondPrice;

        String targetItemName = JOptionPane.showInputDialog(null, "Enter target item name:", "Golden Dragon");

        if (targetItemName == null || targetItemName.trim().isEmpty()) {
            outputWindow.println("No item entered. Exiting.");
            return;
        }
        else{outputWindow.println("Fetching " + targetItemName + " prices...");}

        ArrayList<CheapGDrag> auctionList = new ArrayList<>();

        for (int i = 0; i < totalPages; i++) {
            String url = "https://api.hypixel.net/v2/skyblock/auctions?page=" + i;
            JsonArray auctions = getAuctionsJson(url);
            parseAuctions(auctions, targetItemName, auctionList);
        }

        calculateCoinsPerDay(auctionList);
        sortAndPrintList(auctionList);
    }

    private static JsonArray getAuctionsJson(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection request = url.openConnection();
        request.connect();

        JsonElement root = JsonParser.parseReader(new InputStreamReader(request.getInputStream()));
        JsonObject rootObj = root.getAsJsonObject();
        totalPages = rootObj.get("totalPages").getAsInt();
        return rootObj.getAsJsonArray("auctions");
    }

    private static void parseAuctions(JsonArray auctions, String targetItem, ArrayList<CheapGDrag> list) {
        Pattern lvlPattern = Pattern.compile("\\[Lvl \\d+] ");

        for (JsonElement element : auctions) {
            JsonObject auction = element.getAsJsonObject();
            String itemNameRaw = auction.get("item_name").getAsString();
            Matcher matcher = lvlPattern.matcher(itemNameRaw);
            String itemName = matcher.replaceFirst("");

            if (!itemName.equalsIgnoreCase(targetItem)) continue;

            int startingBid = auction.get("starting_bid").getAsInt();

            int lvl = extractLevelFromItemName(itemNameRaw);
            if (lvl == 200 && startingBid < priceMaxLevel) {
                priceMaxLevel = startingBid;
            }

            String uuid = auction.get("uuid").getAsString();
            list.add(new CheapGDrag(itemName, uuid, startingBid, lvl));
        }
    }

    private static int extractLevelFromItemName(String itemName) {
        try {
            String digits = itemName.substring(5, 8); // indices for digits e.g. "200"
            return Integer.parseInt(digits);
        } catch (Exception e) {
            return 0; // fallback if format unexpected
        }
    }

    private static void calculateCoinsPerDay(ArrayList<CheapGDrag> list) {
        BazaarDataFetcher fetcher = new BazaarDataFetcher();
        BazaarProduct hyperCataBZ= fetcher.getProduct("HYPER_CATALYST");
        double hyperCataPrice = hyperCataBZ.getQuick_status().getSellPrice();
        for (CheapGDrag pet : list) {
            double xpEstimate = (pet.getLevel() - 102) * 1_886_700 + 25_358_785; // rough estimate on how much xp is needed for lvl 200
            double timeToMax = (210_000_000 - xpEstimate) / (7_679_146 + 6_600_000); // divided by Total Taming XP on my main + on my alt account
            double xpVal = (priceMaxLevel - BASE_PRICE) / 210_000_000;
            double xpShared = (210_000_000 - xpEstimate) * 0.25 * xpVal;

            if (timeToMax <= 0) {
                pet.setCoinsPerDay(0);
            } else {
                double taxesAndCandy = priceMaxLevel * 0.035 + timeToMax * 11_400_000;
                double coins = (xpShared + priceMaxLevel - pet.getPrice() - taxesAndCandy - 124*hyperCataPrice * timeToMax + minionLootDaily * timeToMax) / timeToMax;
                pet.setCoinsPerDay(coins);
            }
        }
    }

    private static void sortAndPrintList(ArrayList<CheapGDrag> list) {
        list.sort(Comparator.comparingDouble(CheapGDrag::getCoinsPerDay));
        outputWindow.println("---- Sorted Golden Dragon Auctions ----");
        for (CheapGDrag gdrag : list) {
            outputWindow.println(String.format("Level %d | Price: %,d | Coins/Day: %,d | /viewauction %s",
                    gdrag.getLevel(), gdrag.getPrice(), (int) gdrag.getCoinsPerDay(), gdrag.getUuid()));
        }
    }
}

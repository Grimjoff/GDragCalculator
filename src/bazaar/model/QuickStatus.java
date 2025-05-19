package bazaar.model;

public class QuickStatus {
    private String productId;
    private double sellPrice;
    private double buyPrice;
    private long sellVolume;
    private long buyVolume;
    private long sellMovingWeek;
    private long buyMovingWeek;
    private int sellOrders;
    private int buyOrders;

    public String getProductId() {
        return productId;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public long getSellVolume() {
        return sellVolume;
    }

    public long getBuyVolume() {
        return buyVolume;
    }

    public long getSellMovingWeek() {
        return sellMovingWeek;
    }

    public long getBuyMovingWeek() {
        return buyMovingWeek;
    }

    public int getSellOrders() {
        return sellOrders;
    }

    public int getBuyOrders() {
        return buyOrders;
    }
}

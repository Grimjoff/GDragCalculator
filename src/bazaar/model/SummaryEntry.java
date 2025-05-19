package bazaar.model;

public class SummaryEntry {
    private long amount;
    private double pricePerUnit;
    private int orders;

    public long getAmount() {
        return amount;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public int getOrders() {
        return orders;
    }
}


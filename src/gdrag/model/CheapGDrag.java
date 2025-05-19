package gdrag.model;

public class CheapGDrag {
    private String name;
    private String uuid;
    private int price;
    private int level;
    private double coinsPerDay;

    public CheapGDrag(String name, String uuid, int price, int level) {
        this.name = name;
        this.uuid = uuid;
        this.price = price;
        this.level = level;
        this.coinsPerDay = 0;
    }

    public String getName() { return name; }
    public String getUuid() { return uuid; }
    public int getPrice() { return price; }
    public int getLevel() { return level; }
    public double getCoinsPerDay() { return coinsPerDay; }
    public void setCoinsPerDay(double coinsPerDay) { this.coinsPerDay = coinsPerDay; }
}
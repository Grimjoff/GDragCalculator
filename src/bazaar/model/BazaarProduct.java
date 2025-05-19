package bazaar.model;

import java.util.List;
public class BazaarProduct {
    private String product_id;
    private List<SummaryEntry> sell_summary;
    private List<SummaryEntry> buy_summary;
    private QuickStatus quick_status;

    public String getProduct_id() {
        return product_id;
    }

    public List<SummaryEntry> getSell_summary() {
        return sell_summary;
    }

    public List<SummaryEntry> getBuy_summary() {
        return buy_summary;
    }

    public QuickStatus getQuick_status() {
        return quick_status;
    }
}
package Stocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan Mellem on 5/12/14.
 */
public interface StockDataRetriever {
    // startDate and endDate in form "YYYY-MM-DD"
    ArrayList<HistoricalStockData> getStockData(List<String> symbols, String startDate, String endDate);
}

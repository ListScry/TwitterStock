package Stocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan Mellem on 5/12/14.
 */
public class YQLStockDataRetriever implements StockDataRetriever {
    public ArrayList<HistoricalStockData> getStockData(List<String> symbols, String startDate, String endDate){
        // Initialization
        YQLQueryClient.init();
        YQLHistoricalDataParser.init();

        //Example of query
        /*
        String result = YQLQueryClient.queryJSON("select * from yahoo.finance.historicaldata where symbol in" +
                " (\"MSFT\",\"GOOG\", \"AAPL\") and startDate = \"2014-01-01\" and endDate = \"2014-02-17\"");
        */

        return YQLHistoricalDataParser.parse(YQLQueryClient.queryJSON(YQLQueryClient.getHistoricalDataQueryString(symbols,
                startDate, endDate)));
    }
}
package YQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Stefan Anders Mellem on 2/16/14.
 */

public class TestDriver {

    public static void main(String[] args){
        YQLQueryClient.init();
        YQLHistoricalDataParser.init();

        //Example of query
        /*
        String result = YQLQueryClient.queryJSON("select * from yahoo.finance.historicaldata where symbol in" +
                " (\"MSFT\",\"GOOG\", \"AAPL\") and startDate = \"2014-01-01\" and endDate = \"2014-02-17\"");
        */

        List<String> symbols = Arrays.asList("MSFT","GOOG","AAPL");
        String result = YQLQueryClient.queryJSON(YQLQueryClient.getHistoricalDataQueryString(symbols,
                "2014-01-01","2014-02-17"));

        ArrayList<YQLHistoricalData> data = YQLHistoricalDataParser.parse(result);
        for (YQLHistoricalData datum : data){
            System.out.println(datum.toString());
        }
    }
}

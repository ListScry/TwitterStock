package YQL;

import java.util.ArrayList;

/**
 * Created by Stefan Anders Mellem on 2/16/14.
 */

public class TestDriver {

    public static void main(String[] args){
        YQLQueryClient.init();
        YQLHistoricalDataParser.init();

        String result = YQLQueryClient.queryJSON("select * from yahoo.finance.historicaldata where symbol in" +
                " (\"MSFT\",\"GOOG\", \"AAPL\") and startDate = \"2014-01-01\" and endDate = \"2014-02-17\"");

        ArrayList<YQLHistoricalData> data = YQLHistoricalDataParser.parse(result);
        for (YQLHistoricalData datum : data){
            System.out.println(datum.toString());
        }
    }
}

import SearchTwitter.TweetData;
import YQL.YQLHistoricalData;
import YQL.YQLHistoricalDataParser;
import YQL.YQLQueryClient;
import twitter4j.Status;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.util.*;

import SearchTwitter.*;
import twitter4j.Twitter;

/**
 * Created by frsandstone77 on 2/21/14.
 */
public class Driver {

    static ArrayList<TweetData> tweets;
    static ArrayList<YQLHistoricalData> stocks;

    private static void writeFiles(){
        try {
            // Setup
            PrintStream outTweet = new PrintStream(new FileOutputStream("twitter.txt"));
            PrintStream outStock = new PrintStream(new FileOutputStream("stock.txt"));

            // Output Tweets and Stocks
            for(TweetData tweet : tweets){
                outTweet.println(tweet.toString());
            }

            for (YQLHistoricalData datum : stocks){
                outStock.println(datum.toString());
            }

            // Close Filestreams
            outTweet.close();
            outStock.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void runTwitter(){
        TwitterDriver.setUpTwitter();

        // Setup
        Date date = Calendar.getInstance().getTime();
        String keyword = "\"AAPL\"";

        // Do Query
        ArrayList<Status> statuses = TwitterDriver.queryKeyword(keyword, date);
        tweets = TwitterDriver.convertStatusToTweet(statuses);

        // Do Something With Tweets
        System.out.println("Total Tweets:" + tweets.size());

    }

    private static void runStocks(){

        YQLQueryClient.init();
        YQLHistoricalDataParser.init();

        //Example of query
        /*
        String result = YQLQueryClient.queryJSON("select * from yahoo.finance.historicaldata where symbol in" +
                " (\"MSFT\",\"GOOG\", \"AAPL\") and startDate = \"2014-01-01\" and endDate = \"2014-02-17\"");
        */

        List<String> symbols = Arrays.asList("MSFT", "GOOG", "AAPL");
        String result = YQLQueryClient.queryJSON(YQLQueryClient.getHistoricalDataQueryString(symbols,
                "2014-01-01","2014-02-17"));

        stocks = YQLHistoricalDataParser.parse(result);

    }



    public static void main(String[] args) {
        // Gets tweets from Twitter
        runTwitter();

        // Gets stocks form Yahoo
        runStocks();

        // Write data to file
        writeFiles();

    }

}

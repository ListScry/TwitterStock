import DataStorage.FileStorage;
import DataStorage.TwitterStockDatabase;
import Filtration.*;
import SearchTwitter.TweetData;
import Stocks.*;
import twitter4j.Status;

import java.io.File;

import com.almworks.sqlite4java.*;

import java.text.SimpleDateFormat;
import java.util.*;

import SearchTwitter.*;

import static SearchTwitter.TwitterDriver.deepCopyTweets;

/**
 * Created by frsandstone77 on 2/21/14.
 */
public class Driver {

    static ArrayList<String> filterNames;
    static ArrayList<HistoricalStockData> stocks;
    static ArrayList<TweetData> tweets;
    static ArrayList<ArrayList<TweetData> > filteredTweetLists;

    private static final long MS_IN_DAY = 1000*60*60*24;

    private static void runTwitter(String keyword, Date startDate, Date endDate){
        TwitterDriver.setUpTwitter();

        // Do Query
        ArrayList<Status> statuses = TwitterDriver.queryKeyword(keyword, startDate, endDate);

        // How many tweets did we get?
        System.out.println("Total Tweets:" + statuses.size());

        // Convert from Twitter's Status object to our Tweet data structure
        tweets = TwitterDriver.convertStatusToTweet(statuses);
    }

    private static ArrayList<ArrayList<Filter> > getMyFilterSets(){
        final int NUMFILTERSETS = 3;

        // Set up filtration and/or weighting
        // Base filters
        Filter nof = new NoFilter();
        Filter spf = new SpamFilter();
        Filter rtf100 = new RetweetsFilter(100);
        Filter rtf1000 = new RetweetsFilter(1000);
        Filter rtf10000 = new RetweetsFilter(10000);
        Filter fof100 = new FollowersFilter(100);
        Filter fof1000 = new FollowersFilter(1000);
        Filter fof10000 = new FollowersFilter(10000);
        Filter fef = new FeelFilter();
        Filter fow_lin = new FollowersWeighter(Weighter.WeightingType.LINEAR);
        Filter fow_sqr = new FollowersWeighter(Weighter.WeightingType.SQRT);
        Filter fow_log = new FollowersWeighter(Weighter.WeightingType.LOG);
        Filter fow_asy = new FollowersWeighter(Weighter.WeightingType.EXPASYMP);
        Filter rtw_lin = new RetweetsWeighter(Weighter.WeightingType.LINEAR);
        Filter rtw_sqr = new RetweetsWeighter(Weighter.WeightingType.SQRT);
        Filter rtw_log = new RetweetsWeighter(Weighter.WeightingType.LOG);
        Filter rtw_asy = new RetweetsWeighter(Weighter.WeightingType.EXPASYMP);

        // List of filter sets to be applied (each set will produce its own filtered dataset)
        ArrayList<ArrayList<Filter> > filterSets = new ArrayList<ArrayList<Filter> >(NUMFILTERSETS);
        for (int i=0; i<NUMFILTERSETS; i++)
            filterSets.add(new ArrayList<Filter>());
        filterSets.get(0).add(nof);
        filterSets.get(1).add(spf);
        filterSets.get(2).add(rtf100);
        /*
        filterSets.get(3).add(rtf1000);
        filterSets.get(4).add(rtf10000);
        filterSets.get(5).add(fof100);
        filterSets.get(6).add(fof1000);
        filterSets.get(7).add(fof10000);
        filterSets.get(8).add(fef);
        filterSets.get(9).add(fow_lin);
        filterSets.get(10).add(fow_sqr);
        filterSets.get(11).add(fow_log);
        filterSets.get(12).add(fow_asy);
        filterSets.get(13).add(rtw_lin);
        filterSets.get(14).add(rtw_sqr);
        filterSets.get(15).add(rtw_log);
        filterSets.get(16).add(rtw_asy);
        // Sets with multiple filters
        //filterSets.get(17).add(spf);
        //filterSets.get(17).add(fef);
        */

        return filterSets;
    }

    private static void filterTweets(ArrayList<ArrayList<Filter> > filterSets){
        filteredTweetLists = new ArrayList<ArrayList<TweetData>>(filterSets.size());
        filterNames = new ArrayList<String>(filterSets.size());

        // Run filtration and/or weighting
        for (int i=0 ; i<filterSets.size(); i++){
            filterNames.add("");
            for (Filter filter : filterSets.get(i)){
                ArrayList<TweetData> toBeFiltered = deepCopyTweets(tweets);
                filterNames.set(i, filterNames.get(i).concat(filter.toString()));
                filter.filterTweets(toBeFiltered);
                filteredTweetLists.add(toBeFiltered);
            }
        }
    }

    private static void runStocks(List<String> symbols, Date startDate, Date endDate){
        //data retriever
        StockDataRetriever sdr = new YQLStockDataRetriever();
        //date formatter
        SimpleDateFormat dateNoTime = new SimpleDateFormat("yyyy-MM-dd");

        //retrieve the data
        stocks = sdr.getStockData(symbols, dateNoTime.format(startDate),dateNoTime.format(endDate));
    }


    private static void writeToDatabase(String dbName){
        try {
            for (int i=0; i<filteredTweetLists.size(); i++){
                SQLiteConnection db = TwitterStockDatabase.openDB(new File(dbName+"_"+filterNames.get(i)+".sqlite"));
                System.out.println("After opening database: "+filterNames.get(i)+".");
                System.out.println("Successfully opened created both tables.");
                TwitterStockDatabase.storeTweets(db, filteredTweetLists.get(i));
                TwitterStockDatabase.storeStocks(db, stocks);
                db.dispose();
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // Set up dates and symbols/keywords for which to get data
        //  - dates
        Date today = Calendar.getInstance().getTime();
        Date endDate = new Date(today.getTime() - 3 * MS_IN_DAY);
        Date startDate = new Date(endDate.getTime() - 7 * MS_IN_DAY);
        //  - symbols/keywords
        String twitterKeyword = "\"$AAPL\"";
        List<String> stockSymbols = Arrays.asList("AAPL");


        // Gets tweets from Twitter
	    runTwitter(twitterKeyword,startDate,endDate);

        // Apply filter sets to tweets
        filterTweets(getMyFilterSets());

        // Gets stocks form Yahoo
        runStocks(stockSymbols,startDate,endDate);

        // Write data to file
        FileStorage.writeFiles(filteredTweetLists, stocks);

        System.out.println("Files written.");
        // Input data from files
        //FileStorage.inputFiles(filteredTweetLists, stocks);

        // Write data into database
        writeToDatabase("testdata");
    }

}

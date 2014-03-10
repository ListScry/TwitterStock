package DataAnalysis;

import SearchTwitter.TweetData;
import YQL.YQLHistoricalData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Stefan Mellem on 3/3/14.
 */
public class CompiledData {
    private long start, end;
    private int numDays;
    private ArrayList<ArrayList<TweetData> > tweetBuckets;
    private ArrayList<ArrayList<YQLHistoricalData> > stockBuckets;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public CompiledData(ArrayList<TweetData> tweets, ArrayList<YQLHistoricalData> stocks, long start, long end){
        this.start=start;
        this.end=end;
        //# buckets will be 2*numDays
        numDays = (int)(end-start)/(1000*60*60*24);
        //initialize our buckets
        tweetBuckets = new ArrayList<ArrayList<TweetData> >(2*(int)numDays);
        stockBuckets = new ArrayList<ArrayList<YQLHistoricalData> >(2*(int)numDays);

        //loop through passed-in data and put into the appropriate buckets
        //check timezone information?
    }

    public float getMood(String timestamp){

        return 0.0f;
    }

    public ArrayList<TweetData> getTweets(String timestamp){
        return null;
    }

    public ArrayList<YQLHistoricalData> getStockData(String timestamp){
        return null;
    }

    /*
     date in form "yyyy-MM-dd"
     */
    public long getMarketOpen(String date){
        //append the hour, minute, and timezone
        date+="::HH-mm; zz";
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd::HH-mm");
        try {
            dt = formatter.parse(date);
        }
        catch (ParseException pe){
            pe.printStackTrace();
            return -1;
        }

        return -1;
    }

    /*
     date in form "yyyy-MM-dd"
     */
    public long getMarketClose(String date){

        return -1;
    }

    private int indexFromLong(long timestamp){
        //is the timestamp in our time range?

        //is the first bucket in trading hours or outside them?

        //timestamp in days since epoch
        int daystamp = (int)timestamp/(1000*60*60*24);



        return 0;
    }
}

package DataAnalysis;

import SearchTwitter.TweetData;
import Stocks.HistoricalStockData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Stefan Mellem on 3/3/14.
 */

//TODO: Scrap this and instead add a column in the database with a flag for "before,during,after" maket hours?
//This would entail just shoving this categorization into the preprocessing step

public class CompiledData {
    private long start, end;
    private int numDays;
    private ArrayList<ArrayList<TweetData> > tweetBuckets;
    private ArrayList<ArrayList<HistoricalStockData> > stockBuckets;
    private static final long MS_IN_DAY = 1000*60*60*24;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /*
     start and end in milliseconds since epoch
     start is floored to the beginning of its day
     end is ceilinged to the end of its day
     */
    public CompiledData(ArrayList<TweetData> tweets, ArrayList<HistoricalStockData> stocks, long start, long end){
        long start_floor = (start/MS_IN_DAY)*MS_IN_DAY; //floor to previous day beginning
        long end_ceil = (1+((end-1)/MS_IN_DAY))*MS_IN_DAY; //ceil to next day beginning

        //our first bin will be the market-hours bin on the start day
        //our last bin will be the after-market bin on the end day
        Date start_date = new Date(start_floor);
        Date end_date = new Date(end_ceil);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.start = getMarketOpen(formatter.format(start_date));
        this.end = getMarketOpen(formatter.format(end_date));

        //# buckets will be 2*numDays
        numDays = (int)((this.end-this.start)/MS_IN_DAY);
        //initialize our buckets
        tweetBuckets = new ArrayList<ArrayList<TweetData> >(2*numDays);
        stockBuckets = new ArrayList<ArrayList<HistoricalStockData> >(2*numDays);

        //loop through passed-in data and put into the appropriate buckets
        //check timezone information?
        for (TweetData tweet : tweets){

        }
        for (HistoricalStockData stock : stocks){

        }
    }

    public float getMood(String timestamp){

        return 0.0f;
    }

    public ArrayList<TweetData> getTweets(String timestamp){
        return null;
    }

    public ArrayList<HistoricalStockData> getStockData(String timestamp){
        return null;
    }

    /*
     date in form "yyyy-MM-dd"
     */
    private static long getMarketOpen(String date){
        //append the hour, minute, and timezone
        TimeZone et = TimeZone.getTimeZone("America/New_York");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd::HH-mm z");

        System.out.println("Timezone: " + et.getDisplayName());
        date+="::09-30 "+et.getDisplayName();
        Date dt = new Date();
        try {
            dt = formatter.parse(date);
            if (et.inDaylightTime(dt))
                return dt.getTime()-et.getDSTSavings();
            else
                return dt.getTime();
        }
        catch (ParseException pe){
            pe.printStackTrace();
            return -1;
        }
    }

    /*
     date in form "yyyy-MM-dd"
     */
    private static long getMarketClose(String date){
        //append the hour, minute, and timezone
        TimeZone et = TimeZone.getTimeZone("America/New_York");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd::HH-mm z");
        System.out.print("Timezone: ");
        System.out.println(et.getDisplayName());
        date+="::16-00 "+et.getDisplayName();
        Date dt;
        try {
            dt = formatter.parse(date);
            return dt.getTime();
        }
        catch (ParseException pe){
            pe.printStackTrace();
            return -1;
        }
    }

    private int indexFromLong(long timestamp){
        long diff_from_start = timestamp-start; //milliseconds after the beginning of our first bin
        if (diff_from_start<0 || diff_from_start>end-start)
            return -1; //before start time or after end

        if (diff_from_start%MS_IN_DAY<(6*60+30)*60*1000)
            return (int)(2*diff_from_start/MS_IN_DAY);
        else
            return (int)(2*diff_from_start/MS_IN_DAY+1);
    }
}

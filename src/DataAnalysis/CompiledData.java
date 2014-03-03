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
    private Date startDate;
    private Date endDate;
    private long numDays;
    private ArrayList<ArrayList<TweetData> > tweetBuckets;
    private ArrayList<ArrayList<YQLHistoricalData> > stockBuckets;

    /* CONSTRUCTOR
        start and end in format "yyyy-MM-dd"
     */
    public CompiledData(ArrayList<TweetData> tweets, ArrayList<YQLHistoricalData> stocks, String start, String end){
        //format for start and end datestamps
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            startDate = formatter.parse(start);
            endDate = formatter.parse(end);
        }
        catch (ParseException pe) {
            pe.printStackTrace();
        }

        //# buckets will be 2*numDays
        numDays = (endDate.getTime() - startDate.getTime())/(1000*60*60*24);
        //initialize our buckets
        tweetBuckets = new ArrayList<ArrayList<TweetData> >(2*(int)numDays);
        stockBuckets = new ArrayList<ArrayList<YQLHistoricalData> >(2*(int)numDays);

        //loop through passed-in data and put into the appropriate buckets
    }
}

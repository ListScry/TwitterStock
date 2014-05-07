package SearchTwitter;

import SentimentAnalysis.SentimentAnalyzer;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Created by frsandstone77 on 2/12/14.
 */
public final class TwitterDriver {

    static Twitter twitter;
    static String curKeyword;

    public static void setUpTwitter(){
        //twitter = new TwitterFactory().getSingleton();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("0Roeodg40DTySLsjgcuLJg")
                .setOAuthConsumerSecret("RaJkVNe9bXdEuta2Wqko75tbRgcRFh0W5XMw5MU20gY")
                .setOAuthAccessToken("14666398-eREdxNZuBMpMM1YjzVuPuxxsmytoixoKgDxbuqgU4")
                .setOAuthAccessTokenSecret("l0Wr5r2Y3djYZPLFvGYnz5JBrOaXrt2Zt6wSkIaMnJsvY");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        //for calculating mood values of each tweet as we convert from Status to TweetData
        SentimentAnalyzer.init();
    }


    public static QueryResult performQuery(String keyword, long id_before){
        try{
            // Set up query
            Query query = new Query();
            query.query(keyword);
            query.setCount(100);
            // Set the maximum ID so that we don't get duplicate results.
            // -1 signifies first query in this set
            if( id_before != -1)
                query.setMaxId(id_before);

            // Limit the locale to only English
            query.locale("en");
            //query.resultType("popular");

            System.out.println("Querying from id " + query.getSinceId() + " up to id: " + query.getMaxId());

            // Set up result
            QueryResult result = twitter.search(query);

            System.out.println("Received " + result.getCount() + " tweets.");
            return result;

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to show status: " + te.getMessage());
            System.exit(-1);
        }

        return null;
    }

    // Get earliest tweet - used to query for "only tweets older than X"
    public static long oldestResult(QueryResult result){

        long earliestID = result.getTweets().get(0).getId();
        for( Status status : result.getTweets() ){
            if( status.getId() < earliestID ){
                earliestID = status.getId();
                System.out.println(status.getCreatedAt());
            }
        }
        return earliestID;
    }

    // temporary - delete later
    public static void printIDs(QueryResult result){
        for( Status status : result.getTweets() ){  System.out.println("" + status.getId());    }
    }

    public static void printStatus(Status status){
        String message = status.getText();
        String username = status.getUser().getScreenName();
        String date = status.getCreatedAt().toString();

        System.out.println("@" + username);
        System.out.println("\t" + message);
        System.out.println("\t" + date);
    }

    public static void printResults(QueryResult result){
        int numTweets = result.getCount();


        for (Status status : result.getTweets()) {
            printStatus(status);
        }
        System.out.println("---------------------");
        System.out.println(result.getTweets().size());
    }

    public static Date addDay(Date date, int num){
        // LOL this is awful.
        // Only way I can find to subtract a day from a Date object
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(date);
        tempCal.add(Calendar.DATE, num);

        return tempCal.getTime();
    }

    // Helper method to perform queries for the entire week
    // TODO: actually add the performQuery portion of this
    public static void queryWeek(){
        String startDate_str, endDate_str;
        Date startDate,endDate;

        // for loop through past week
        startDate = Calendar.getInstance().getTime();
        for(int day = 0; day < 6; day ++){
            endDate = addDay(startDate, 1);

            startDate_str = "" + startDate.getYear() + "-" + startDate.getMonth() + startDate.getDay();
            endDate_str = "" + endDate.getYear() + "-" + endDate.getMonth() + endDate.getDay();

            startDate = addDay(startDate,-1);
        }
    }

    public static ArrayList<Status> queryKeyword(String keyword, Date date){

        int totalTweets = 15000; // max 180 queries per 15 minutes
        int resultsPerQuery = 100;
        int numQueries = totalTweets / resultsPerQuery;

        // Used later when putting data together.
        curKeyword = keyword;

        ArrayList<Status> allStatuses = new ArrayList<Status>();

        // ------------- Do date stuff
        Date startDate = addDay(date,-7); //start 1 week prior to date
        Date endDate = date; //end at passed date (default today in Driver.java)

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String startDate_str = formatter.format(startDate);
        String endDate_str = formatter.format(endDate);

        startDate_str = " since:" +  startDate_str;
        endDate_str = " until:"  + endDate_str;
        // ------------- End doing date stuff

        QueryResult result;
        String queryString = keyword + startDate_str + endDate_str;
        System.out.println(queryString);
        long lastID = -1;
        long previousLastID;
        for(int x = 0; x < numQueries; x ++){
            result = performQuery(queryString, lastID);
            previousLastID = lastID;
            lastID = oldestResult(result);
            //System.out.println("-------" + (x * resultsPerQuery) + "-------");

            // Store all of these statuses
            for(Status status : result.getTweets()){
                // TODO: Skip if this tweet is outside the range.  Twitter API bug
                allStatuses.add(status);
            }

            if (lastID==previousLastID)
                break;
        }



        return allStatuses;
    }

    public static void setMood(TweetData tweet){
        tweet.Mood = SentimentAnalyzer.findSentiment(tweet.Text);
    }

    public static ArrayList<TweetData> convertStatusToTweet(ArrayList<Status> statuses){

        ArrayList<TweetData> tweets = new ArrayList<TweetData>();

        // Used to format date to...below format.
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        TweetData newTweet;
        for(Status status : statuses){
            newTweet = new TweetData();

            //System.out.println(status.getUser().getScreenName().toString());
            //System.out.println(status.getCreatedAt());
            //it appears that all times are PACIFIC

            // Prep with data
            newTweet.ID         =   "" + status.getId();
            newTweet.User       =   status.getUser().getScreenName().toString();
            newTweet.Followers  =   status.getUser().getFollowersCount();
            newTweet.Retweets   =   status.getRetweetCount();
            newTweet.TimeStamp  =   status.getCreatedAt().getTime();
            newTweet.Date       =   formatter.format(status.getCreatedAt());
            // Mood goes here, sequentially
            newTweet.Keyword    =   curKeyword;
            newTweet.DateBin    =   TweetBinner.getDateBin(newTweet.TimeStamp);
            newTweet.Text       =   status.getText().toString();

            // Calculate and set Mood
            setMood(newTweet);

            // Add to list
            tweets.add(newTweet);
        }


        return tweets;
    }



    /*public static void main(String[] args) {
        setUpTwitter();

        // Setup
        Date date = Calendar.getInstance().getTime();
        String keyword = "\"AAPL\"";

        // Do Query
        ArrayList<Status> statuses = queryKeyword(keyword,date);
        ArrayList<TweetData> tweets = convertStatusToTweet(statuses);


        // Do Something With Tweets 
        System.out.println("Total Tweets:" + tweets.size());
        for(TweetData tweet : tweets){
            System.out.println( tweet.toString() );
        }


    }*/
}

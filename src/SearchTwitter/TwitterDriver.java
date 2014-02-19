package SearchTwitter;

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

    private static void setUpTwitter(){
        //twitter = new TwitterFactory().getSingleton();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("0Roeodg40DTySLsjgcuLJg")
                .setOAuthConsumerSecret("RaJkVNe9bXdEuta2Wqko75tbRgcRFh0W5XMw5MU20gY")
                .setOAuthAccessToken("14666398-eREdxNZuBMpMM1YjzVuPuxxsmytoixoKgDxbuqgU4")
                .setOAuthAccessTokenSecret("l0Wr5r2Y3djYZPLFvGYnz5JBrOaXrt2Zt6wSkIaMnJsvY");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }


    private static QueryResult performQuery(String keyword, long id_before){
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

            query.resultType("popular");

            // Set up result
            QueryResult result = twitter.search(query);
            return result;

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to show status: " + te.getMessage());
            System.exit(-1);
        }

        return null;
    }

    // Get earliest tweet - used to query for "only tweets older than X"
    private static long oldestResult(QueryResult result){

        long earliestID = result.getTweets().get(0).getId();
        for( Status status : result.getTweets() ){
            if( status.getId() < earliestID )
                earliestID = status.getId();

        }
        return earliestID;
    }

    // temporary - delete later
    private static void printIDs(QueryResult result){
        for( Status status : result.getTweets() ){  System.out.println("" + status.getId());    }
    }

    private static void printStatus(Status status){
        String message = status.getText();
        String username = status.getUser().getScreenName();
        String date = status.getCreatedAt().toString();

        System.out.println("@" + username);
        System.out.println("\t" + message);
        System.out.println("\t" + date);
    }

    private static void printResults(QueryResult result){
        int numTweets = result.getCount();


        for (Status status : result.getTweets()) {
            printStatus(status);
        }
        System.out.println("---------------------");
        System.out.println(result.getTweets().size());
    }

    private static Date addDay(Date date, int num){
        // LOL this is awful.
        // Only way I can find to subtract a day from a Date object
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(date);
        tempCal.add(Calendar.DATE, num);

        return tempCal.getTime();
    }

    // Helper method to perform queries for the entire week
    // TODO: actually add the performQuery portion of this
    private static void queryWeek(){
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

    private static ArrayList<Status> queryKeyword(String keyword, Date date){

        int totalTweets = 60;
        int resultsPerQuery = 15;
        int numQueries = totalTweets / resultsPerQuery;

        ArrayList<Status> allStatuses = new ArrayList<Status>();

        // ------------- Do date stuff
        Date startDate = addDay(date,-1);
        Date endDate = date;

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
        for(int x = 0; x < numQueries; x ++){
            result = performQuery(queryString, lastID);
            lastID = oldestResult(result);
            //System.out.println("-------" + (x * resultsPerQuery) + "-------");

            // Store all of these statuses
            for(Status status : result.getTweets()){
                // TODO: Skip if this tweet is outside the range.  Twitter API bug
                allStatuses.add(status);
            }
        }

        return allStatuses;
    }

    public static void main(String[] args) {

        setUpTwitter();

        // Setup
        Date date = Calendar.getInstance().getTime();
        String keyword = "\"Apple\"";

        // Do Query
        ArrayList<Status> tweets = queryKeyword(keyword,date);

        // Do Something With Tweets 
        System.out.println("Total Tweets:" + tweets.size());
        /*for(Status status : tweets){
            System.out.println(status.getCreatedAt());
        }*/


    }
}

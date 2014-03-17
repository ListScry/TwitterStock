import DataAnalysis.CompiledData;
import SearchTwitter.TweetData;
import YQL.YQLHistoricalData;
import YQL.YQLHistoricalDataParser;
import YQL.YQLQueryClient;
import twitter4j.Status;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import com.almworks.sqlite4java.*;

import java.util.*;

import SearchTwitter.*;
import twitter4j.Twitter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


    private static TweetData readTweetFromFile(Scanner sc2){
        //Scanner s2 = new Scanner(sc2.nextLine());
        TweetData curTweet = new TweetData();

        //ID
        curTweet.ID = sc2.next();

        // USER
        curTweet.User = sc2.next();

        // FOLLOWERS
        curTweet.Followers = sc2.next();

        // RETWEETS
        curTweet.Retweets = sc2.next();

        // TIMESTAMP
        curTweet.TimeStamp = sc2.next();

        // MOOD
        curTweet.Mood = sc2.next();

        // KEYWORD
        curTweet.Keyword = sc2.next();

        String completeText = "";
        while ( sc2.hasNext() ) {
            String curWord = sc2.next();

            if( curWord.equals(curTweet.DELIM) )
                break;

            completeText += " " + curWord;
        }

        // TEXT
        curTweet.Text = completeText;

        return curTweet;
    }

    private static YQLHistoricalData readStockFromFile(Scanner sc2){
        //Scanner s2 = new Scanner(sc2.nextLine());
        YQLHistoricalData curStock = new YQLHistoricalData();

        //ID ---> don't save, there for unique ID
        sc2.next();

        // SYMBOL
        curStock.Symbol = sc2.next();

        // DATE
        curStock.Date = sc2.next();

        // OPEN
        curStock.Open = sc2.next();

        // HIGH
        curStock.High = sc2.next();

        // LOW
        curStock.Low = sc2.next();

        // CLOSE
        curStock.Close = sc2.next();

        // VOLUME
        curStock.Volume = sc2.next();

        // ADJ_CLOSE
        curStock.Adj_Close = sc2.next();

        return curStock;
    }

    public static void inputFiles(){
        Scanner sc2 = null;
        boolean b;

        // INITIALIZATION STUFF
        tweets = new ArrayList<TweetData>();
        stocks = new ArrayList<YQLHistoricalData>();
        TweetData curTweet;
        YQLHistoricalData curStock;


        // READ IN TWEET DATA
        try {
            sc2 = new Scanner(new File("twitter.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (b = sc2.hasNext()) {
            curTweet = readTweetFromFile(sc2);
            tweets.add(curTweet);
        }

        // READ IN STOCK DATA
        try {
            sc2 = new Scanner(new File("stock.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (b = sc2.hasNext()) {
            curStock = readStockFromFile(sc2);

            System.out.println(curStock);

            stocks.add(curStock);
        }


    }

    private static void initDB(){
        SQLiteConnection db = new SQLiteConnection(new File("./database"));

        try { db.open(true); // Create first table

            String s3 = "INSERT INTO QUOTE";

            //System.out.println(st);

            String select = "SELECT * FROM Quote;";
            SQLiteStatement st2 = db.prepare(select);

            while (st2.step()) {
                System.out.println(st2);
            }


        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    private static void store(SQLiteConnection db, TweetData td) 
	throws SQLiteException {
	String q = "INSERT INTO Tweets VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	SQLiteStatement st = db.prepare(q);
	st.bind(1, td.ID);
	st.bind(2, td.User);
	st.bind(3, Integer.parseInt(td.Followers));
	st.bind(4, td.getDate());
	st.bind(5, td.Mood);
	st.bind(6, td.Keyword);
	st.bind(7, td.Text);
	st.step();
	st.dispose();
    }

    private static void initDB(File databaseFile) throws SQLiteException {
        SQLiteConnection db = new SQLiteConnection(databaseFile);
	db.open(true);
	String s = "CREATE TABLE Quote ("
	    + "ID varchar(30), Ticker varchar(5), Timestamp BIGINT, "
	    + "Mood varchar(30), Keyword varchar(30), Volume int, Adj_close Decimal(4,2) );";
	SQLiteStatement st = db.prepare(s);
	st.step();
	st.dispose();
	String t = "CREATE TABLE Tweets (" 
	    + "ID varchar(30), User varchar(30), Followers Bigint "
	    + "Timestamp Bigint, Mood varchar(30), Keyword varchar(30) "
	    + "Text varchar(140) );";
	st = db.prepare(s);
	st.step();
	st.dispose();
    }


    public static void main(String[] args) {
        // Gets tweets from Twitter
        runTwitter();

        // Gets stocks form Yahoo
        runStocks();

        // Write data to file
        writeFiles();

        //inputFiles();

        //initDB();

    }

}

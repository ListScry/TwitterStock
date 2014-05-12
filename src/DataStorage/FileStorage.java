package DataStorage;

import SearchTwitter.TweetData;
import Stocks.HistoricalStockData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Stefan Mellem on 5/12/14.
 */
public class FileStorage {
    public static void writeFiles(ArrayList<ArrayList<TweetData> > tweetLists, ArrayList<HistoricalStockData> stocks){
        try {
            // Setup
            ArrayList<PrintStream> outTweetStreams = new ArrayList<PrintStream>();
            for (int i=0; i<tweetLists.size(); i++){
                outTweetStreams.add(new PrintStream(new FileOutputStream("twitter"+i+".txt")));
            }
            PrintStream outStock = new PrintStream(new FileOutputStream("stock.txt"));

            // Output Tweets and Stocks
            for (int i=0; i<tweetLists.size(); i++){
                writeTweets(outTweetStreams.get(i), tweetLists.get(i));
            }

            writeStocks(outStock,stocks);

            // Close Filestreams
            for (int i=0; i<tweetLists.size(); i++){
                outTweetStreams.get(i).close();
            }
            outStock.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeTweets(PrintStream ps, ArrayList<TweetData> tweets){
        try {
            for(TweetData tweet : tweets){
                ps.println(tweet.toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void writeStocks(PrintStream ps, ArrayList<HistoricalStockData> stocks){
        try {
            for (HistoricalStockData datum : stocks){
                ps.println(datum.toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static TweetData readTweetFromFile(Scanner sc2){
        //Scanner s2 = new Scanner(sc2.nextLine());
        TweetData curTweet = new TweetData();

        // ID
        curTweet.ID = sc2.next();
        // USER
        curTweet.User = sc2.next();
        // FOLLOWERS
        curTweet.Followers = Integer.parseInt(sc2.next());
        // RETWEETS
        curTweet.Retweets = Integer.parseInt(sc2.next());
        // TIMESTAMP
        curTweet.TimeStamp = Long.parseLong(sc2.next());
        // DATE
        curTweet.Date = sc2.next();
        // MOOD
        curTweet.Mood = Float.parseFloat(sc2.next());
        // WEIGHT
        curTweet.Weight = Float.parseFloat(sc2.next());
        // KEYWORD
        curTweet.Keyword = sc2.next();
        // DATEBIN
        curTweet.DateBin = sc2.next();
        // TEXT
        String completeText = "";
        while ( sc2.hasNext() ) {
            String curWord = sc2.next();

            if( curWord.equals(curTweet.DELIM) )
                break;

            completeText += " " + curWord;
        }
        curTweet.Text = completeText;

        return curTweet;
    }

    private static HistoricalStockData readStockFromFile(Scanner sc2){
        //Scanner s2 = new Scanner(sc2.nextLine());
        HistoricalStockData curStock = new HistoricalStockData();

        //ID ---> don't save, there for unique ID (can be generated from other data that we are saving)
        sc2.next();
        // SYMBOL
        curStock.Symbol = sc2.next();
        // DATE
        curStock.Date = sc2.next();
        // OPEN
        curStock.Open = Float.parseFloat(sc2.next());
        // HIGH
        curStock.High = Float.parseFloat(sc2.next());
        // LOW
        curStock.Low = Float.parseFloat(sc2.next());
        // CLOSE
        curStock.Close = Float.parseFloat(sc2.next());
        // VOLUME
        curStock.Volume = Long.parseLong(sc2.next());
        // ADJ_CLOSE
        curStock.Adj_Close = Float.parseFloat(sc2.next());

        return curStock;
    }

    // Arguments are initialized and filled with data
    public static void inputFiles(ArrayList<ArrayList<TweetData> > tweetLists, ArrayList<HistoricalStockData> stocks){
        Scanner sc2 = null;
        boolean b;

        // INITIALIZATION STUFF
        tweetLists = new ArrayList<ArrayList<TweetData> >();
        stocks = new ArrayList<HistoricalStockData>();
        TweetData curTweet;
        HistoricalStockData curStock;


        // READ IN TWEET DATA
        try {
            int i=0;
            while (true){
                sc2 = new Scanner(new File("twitter"+i+".txt"));
                tweetLists.add(new ArrayList<TweetData>());
                while (b = sc2.hasNext()) {
                    curTweet = readTweetFromFile(sc2);
                    tweetLists.get(i).add(curTweet);
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

}

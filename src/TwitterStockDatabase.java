import SearchTwitter.TweetData;
import Stocks.HistoricalStockData;
import com.almworks.sqlite4java.SQLite;
import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Stefan Mellem on 5/12/14.
 */
public class TwitterStockDatabase {
    private static void store(SQLiteConnection db, TweetData td)
            throws SQLiteException {
        if (! db.isOpen()) {
            db.open(false);//open the database if it is not open
        }
        String q = "INSERT INTO Tweets VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        SQLiteStatement st = db.prepare(q);
        st.bind(1, td.ID);
        st.bind(2, td.User);
        st.bind(3, td.Followers);
        st.bind(4, td.Retweets);
        st.bind(5, td.TimeStamp);
        st.bind(6, td.Date);
        st.bind(7, td.Mood);
        st.bind(8, td.Weight);
        st.bind(9, td.Keyword);
        st.bind(10, td.DateBin);
        st.bind(11, td.Text);
        st.step();
        st.dispose();
    }

    private static void store(SQLiteConnection db, HistoricalStockData yd)
            throws SQLiteException {
        if(!db.isOpen()) {
            db.open(false);//open the database if it is not open
        }
        String q = "INSERT INTO Quote VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        SQLiteStatement st = db.prepare(q);
        st.bind(1, yd.id());
        st.bind(2, yd.Symbol);
        st.bind(3, yd.Date);
        st.bind(4, yd.Open);
        st.bind(5, yd.High);
        st.bind(6, yd.Low);
        st.bind(7, yd.Close);
        st.bind(8, yd.Volume);
        st.bind(9, yd.Adj_Close);
        st.step();
        st.dispose();
    }

    public static void storeTweets(SQLiteConnection db, ArrayList<TweetData> tweets){
        for(TweetData tweet : tweets) {
            try{
                store(db, tweet);
            }
            catch(SQLiteException sqle){
                sqle.printStackTrace();
            }
        }
    }

    public static void storeStocks(SQLiteConnection db, ArrayList<HistoricalStockData> stocks){
        for (HistoricalStockData stock : stocks) {
            try{
                store(db, stock);
            }
            catch(SQLiteException sqle){
                sqle.printStackTrace();
            }
        }
    }

    public static SQLiteConnection openDB(File databaseFile) throws SQLiteException {
        SQLiteConnection db = new SQLiteConnection(databaseFile);
        try {// Try opening it and not allow it to create
            db.open(false);
            System.out.println("Database already existed.");
            return db;
        } catch(SQLiteException ex) {
            System.out.println("Database did not already exist.");
            //If that did not work, try opening it allowing a create
            // and make the tables
            db.open(true);
            String s = "CREATE TABLE Quote ("
                    + "ID varchar(30), Ticker varchar(5), Date varchar(10), "
                    + "Open Decimal(4,2), High Decimal(4,2), Low Decimal(4,2), Close Decimal(4,2), "
                    + "Volume bigint, Adj_close Decimal(4,2) );";
            SQLiteStatement st = db.prepare(s);
            st.step();
            st.dispose();
            String t = "CREATE TABLE Tweets ("
                    + "ID varchar(30), User varchar(30), Followers Bigint, Retweets bigint, "
                    + "Timestamp Bigint, Date varchar(10), Mood varchar(30), Weight varchar(30), Keyword varchar(30), "
                    + "DateBin varchar(13), Text varchar(140) );";
            st = db.prepare(t);
            st.step();
            st.dispose();
            return db;
        }
    }
}

package SearchTwitter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by frsandstone77 on 2/18/14.
 */
public class TweetData {
    public static final String DELIM = "%$#@%";

    public String ID;
    public String User;
    public int Followers;
    public int Retweets;
    public long TimeStamp;
    public String Date;
    public float Mood;
    public float Weight;
    public String Keyword;
    public String DateBin; //<DATE>::0 is before market hours, <DATE>::1 is during market hours
    public String Text;

    public TweetData(){}

    public TweetData(TweetData td){
        this.ID = td.ID;
        this.User = td.User;
        this.Followers = td.Followers;
        this.Retweets = td.Retweets;
        this.TimeStamp = td.TimeStamp;
        this.Date = td.Date;
        this.Mood = td.Mood;
        this.Weight = td.Weight;
        this.Keyword = td.Keyword;
        this.DateBin = td.DateBin;
        this.Text = td.Text;
    }

    private String getDateAsString(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date(TimeStamp));
    }

    public String toString(){
        return ID+ " " + User + " "+Followers+" "+Retweets+" "+TimeStamp+" "+Date+" "+Mood+" "+Weight+" "+Keyword
                +" "+DateBin+" "+Text+ " " + DELIM;
    }
}
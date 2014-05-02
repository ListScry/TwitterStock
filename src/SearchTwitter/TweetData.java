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
    public String Keyword;
    public String DateBin; //<DATE>::0 is before market hours, <DATE>::1 is during market hours
    public String Text;

    private String getDateAsString(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date(TimeStamp));
    }

    public String toString(){
        return ID+ " " + User + " "+Followers+" "+Retweets+" "+TimeStamp+" "+Date+" "+Mood+" "+Keyword+" "+DateBin+" "+Text+ " " + DELIM;
    }
}

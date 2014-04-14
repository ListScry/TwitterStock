package SearchTwitter;

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
    public float Mood;
    public String Keyword;
    public int BinFlag; //-1 is before market hours, 0 is during, 1 is after
    public String Text;

    public String toString(){
        return ID+ " " + User + " "+Followers+" "+Retweets+" "+TimeStamp+" "+Mood+" "+Keyword+" "+BinFlag+" "+Text+ " " + DELIM;
    }
}

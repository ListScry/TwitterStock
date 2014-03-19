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
    public String Text;

    public String toString(){
        return ID+ " " + User + " "+Followers+" "+Retweets+" "+TimeStamp+" "+Mood+" "+Keyword+" "+Text+ " " + DELIM;
    }
}

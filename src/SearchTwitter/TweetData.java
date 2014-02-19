package SearchTwitter;

/**
 * Created by frsandstone77 on 2/18/14.
 */
public class TweetData {
   private static final String DELIM = "%$#@%";

    public String ID;
    public String User;
    public String Followers;
    public String Retweets;
    public String TimeStamp;
    public String Mood;
    public String Keyword;
    public String Text;

    public String toString(){
        return ID+ " " + User + " "+Followers+" "+Retweets+" "+TimeStamp+" "+Mood+" "+Keyword+" "+Text+DELIM;
    }
}

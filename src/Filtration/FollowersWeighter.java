package Filtration;

import SearchTwitter.TweetData;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Stefan Mellem on 5/7/14.
 */
public class FollowersWeighter extends Weighter {

    public FollowersWeighter(){
        this.type = WeightingType.LINEAR;
    }

    public FollowersWeighter(WeightingType type){
        this.type=type;
    }

    public void filterTweets(ArrayList<TweetData> tweets){
        for (Iterator<TweetData> i = tweets.iterator(); i.hasNext();){
            TweetData tweet = i.next();

            switch (type){
                case LINEAR:
                    tweet.Mood*=tweet.Followers;
                    break;
                case SQRT:
                    tweet.Mood*=Math.sqrt(tweet.Followers);
                    break;
                case LOG:
                    tweet.Mood*=Math.log(tweet.Followers);
                    break;
                case EXPASYMP:
                    tweet.Mood*=1-1.0/tweet.Followers;
                    break;
                default:
                    System.err.println("[ERROR]: invalid weighting type; no weighting performed");
                    break;
            }
        }
    }

    public void setType(WeightingType type){
        this.type = type;
    }

    public WeightingType getType(){
        return type;
    }

    public String toString(){
        switch (type){
            case LINEAR:
                return "FollowersWeighter with type: LINEAR";
            case SQRT:
                return "FollowersWeighter with type: SQRT";
            case LOG:
                return "FollowersWeighter with type: LOG";
            case EXPASYMP:
                return "FollowersWeighter with type: EXPASYMP";
            default:
                return "FollowersWeighter with type: [INVALID TYPE]";
        }
    }
}

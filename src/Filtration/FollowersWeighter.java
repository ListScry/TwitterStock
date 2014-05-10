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
                    tweet.Weight*=tweet.Followers;
                    break;
                case SQRT:
                    tweet.Weight*=Math.sqrt(tweet.Followers);
                    break;
                case LOG:
                    tweet.Weight*=Math.log(tweet.Followers);
                    break;
                case EXPASYMP:
                    tweet.Weight*=1-1.0/(1+tweet.Followers);
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
                return "FollowersWeighterLINEAR";
            case SQRT:
                return "FollowersWeighterSQRT";
            case LOG:
                return "FollowersWeighterLOG";
            case EXPASYMP:
                return "FollowersWeighterEXPASYMP";
            default:
                return "FollowersWeighter[INVALID TYPE]";
        }
    }
}

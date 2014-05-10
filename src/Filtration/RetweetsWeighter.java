package Filtration;

import SearchTwitter.TweetData;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Stefan Mellem on 5/7/14.
 */
public class RetweetsWeighter extends Weighter {

    public RetweetsWeighter(){
        this.type = WeightingType.LINEAR;
    }

    public RetweetsWeighter(WeightingType type){
        this.type=type;
    }

    public void filterTweets(ArrayList<TweetData> tweets){
        for (Iterator<TweetData> i = tweets.iterator(); i.hasNext();){
            TweetData tweet = i.next();

            switch (type){
                case LINEAR:
                    tweet.Weight*=tweet.Retweets;
                    break;
                case SQRT:
                    tweet.Weight*=Math.sqrt(tweet.Retweets);
                    break;
                case LOG:
                    tweet.Weight*=Math.log(tweet.Retweets);
                    break;
                case EXPASYMP:
                    tweet.Weight*=1-1.0/(1+tweet.Retweets);
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
                return "RetweetsWeighterLINEAR";
            case SQRT:
                return "RetweetsWeighterSQRT";
            case LOG:
                return "RetweetsWeighterLOG";
            case EXPASYMP:
                return "RetweetsWeighterEXPASYMP";
            default:
                return "RetweetsWeighter[INVALID TYPE]";
        }
    }
}
